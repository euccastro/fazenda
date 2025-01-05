# Fazenda

This is mostly a playground project to experiment with [Electric 3](https://github.com/hyperfiddle/electric) with a [Datalevin](https://github.com/juji-io/datalevin) backend.

Although I have been given permission to publish this repository, Electric 3 is in closed beta, so if you want to give this a try you should request access by DMing Dustin Getz in the [Clojurians Slack](https://clojurians.slack.com).  If you do that, you may want to start with the bare Electric 3 starter app, upon which this is based.  I don't expect to do anything particularly groundbreaking here.  Joining the #hyperfiddle channel there is recommended.

The project I'm going to work towards is: make a Datalevin DB for whatever I may want to store for personal use (quick note-taking, TODOs, grocery lists, checklists for various contexts, etc.), aiming for a flexible schema here, and have Electric 3 as a frontend for this, aiming for pleasant-enough (for me) interaction in mobile and desktop browsers.

Eventually I may develop other clients (ClojureDart?  HumbleUI?) to interact with the same DB, but for the moment I'll let the Electric 3 app own the Datalevin (i.e., LMDB) database.

This is not meant to ever be used by anyone besides me, but I'll probably publish a demo page somewhere (TBD).

What follows is the original README of the Electric v3 Starter App.  I'll try and minimize changes to the app and hence to this part of the README, to make it easier to import upstream changes.

## Instructions

Dev build:

* Shell: `clj -A:dev -X dev/-main`, or repl: `(dev/-main)`
  * authentication instructions will be printed
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
- fly.io deployment: [fly.toml](fly.toml)
- github actions + fly: [.github/workflows/deploy.yml](.github/workflows/deploy.yml)
