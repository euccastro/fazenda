# Fazenda

This is mostly a playground project to experiment with [Electric 3](https://github.com/hyperfiddle/electric) with a [Datalevin](https://github.com/juji-io/datalevin) backend.

Although I have been given permission to publish this repository, Electric 3 is in closed beta, so if you want to give this a try you should request access by DMing Dustin Getz in the [Clojurians Slack](https://clojurians.slack.com).  If you do that, you may want to start with the bare Electric 3 starter app, upon which this is based.  I don't expect to do anything particularly groundbreaking here.  Joining the #hyperfiddle channel there is recommended.

The project I'm going to work towards is: make a Datalevin DB for whatever I may want to store for personal use (quick note-taking, TODOs, grocery lists, checklists for various contexts, etc.), aiming for a flexible schema here, and have Electric 3 as a frontend for this, aiming for pleasant-enough (for me) interaction in mobile and desktop browsers.

Eventually I may develop other clients (ClojureDart?  HumbleUI?) to interact with the same DB, but for the moment I'll let the Electric 3 app own the Datalevin (i.e., LMDB) database.

This is not meant to ever be used by anyone besides me, but I'll probably publish a demo page somewhere (TBD).

## Devops

The datalevin DB will be created at `$HOME/dtlv`. You may change this at `devops.db/db-dir`.

# Electric v3 Starter App README

What follows is the original README of the Electric v3 Starter App.  I'll try and minimize changes to the app and hence to this part of the README, to make it easier to import upstream changes.

## Links

* Electric github with source code: https://github.com/hyperfiddle/electric
* Tutorial: https://electric.hyperfiddle.net/ (we'll be fleshing out this as a full docs site asap)

## Getting started

* Shell: `clj -A:dev -X dev/-main`. 
* Login instructions will be printed
* REPL: `:dev` deps alias, `(dev/-main)` at the REPL to start dev build
* App will start on http://localhost:8080
* Electric root function: [src/electric_starter_app/main.cljc](src/electric_starter_app/main.cljc)
* Hot code reloading works: edit -> save -> see app reload in browser

```shell
# Prod build
clj -X:build:prod build-client
clj -M:prod -m prod

# Uberjar (optional)
clj -X:build:prod uberjar :build/jar-name "app.jar"
java -cp target/app.jar clojure.main -m prod

# Docker
docker build -t electric3-starter-app:latest .
docker run --rm -it -p 8080:8080 electric3-starter-app:latest
```

## License
Electric v3 is **free for bootstrappers and non-commercial use,** and otherwise available commercially under a business source available license, see: [Electric v3 license change](https://tana.pub/lQwRvGRaQ7hM/electric-v3-license-change) (2024 Oct). License activation is experimentally implemented through the Electric compiler, requiring **compile-time** login for **dev builds only**. That means: no license check at runtime, no login in prod builds, CI/CD etc, no licensing code even on the classpath at runtime. This is experimental, but seems to be working great so far. We do not currently require any account approval steps, just log in. There will be a EULA at some point once we finalize the non-commercial license, for now we are focused on enterprise deals which are different.
