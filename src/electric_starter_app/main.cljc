(ns electric-starter-app.main
  (:require [hyperfiddle.electric3 :as e]
            [hyperfiddle.electric-dom3 :as dom]
            #?(:clj [electric-starter-app.db :as db])))

(e/declare db)

;; Adapted from form/Input, removing some options I'm not using
(e/defn Textarea [v props]
  (e/client
    (dom/textarea
      (dom/props props)
      ;; don't reveal :grow/:shrink when the `if` switches
      ;; (Note: Reconcile is discrete, so it will not even emit :change on switch)
      (e/Reconcile
       (if (dom/Focused?)
         (dom/On "input" #(-> % .-target .-value) (e/snapshot (str v)))
         (set! (.-value dom/node) (str v)))))))

(e/defn In []
  (dom/div
    (let [s0 (e/server (db/?in db db/username))
          !s (atom s0)
          s (e/watch !s)]
      (reset! !s (dom/div (Textarea s {:cols 90 :rows 40 })))
      (let [t (dom/button (dom/text "Submit")
                          (let [e (dom/On "click" identity nil)
                                [t err] (e/Token e)]
                            (dom/props {:aria-busy (some? t)
                                        :disabled (some? t)
                                        :aria-invalid (some? err)})
                            t))
            t' (dom/button (dom/text "Reset")
                           (let [e (dom/On "click" identity nil)
                                 [t'] (e/Token e)]
                             (dom/props {:aria-busy (some? t)
                                         :disabled (some? t)})
                             t'))]
        (when t'
          (reset! !s s0)
          (t'))
        (when t
          (let [res (e/server
                      (e/Offload
                       (fn []
                         (try (db/!in db/username s)
                              (catch Exception e (pr-str e)))
                         nil)))]
            (if res
              (t res)
              (t))))))))

(e/defn Main [ring-request]
  (e/client
    (binding [dom/node js/document.body
              e/http-request (e/server ring-request)
              db (e/server (e/watch (db/conn)))]
      ;; mandatory wrapper div https://github.com/hyperfiddle/electric/issues/74
      (dom/div (dom/props {:style {:display "contents"}})
               (dom/h2 (dom/text "In"))
               (In)))))
