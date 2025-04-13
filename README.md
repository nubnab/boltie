# Boltie
An all-in-one streaming website package written in Java, powered by [OvenMediaEngine](https://github.com/AirenSoft/OvenMediaEngine)

---

## Project Overview
**Boltie** aims to provide an easy to set up streaming website, akin to Twitch, that offers WebRTC streaming capability for low latency streams thanks to [OvenMediaEngine](https://github.com/AirenSoft/OvenMediaEngine). This is a fullstack learning project, where my aim is to play around with new concepts as I learn them and also get to know scalability 
and clusters while I am at it.

---

## Features
- **WebRTC** -  sub-second latency streaming, **LLHS** capability in case of bad connection.
- **Recordings** - automatic recordings during streaming. Immediate ability to watch recordings during streaming.
- **Chat rooms** - every stream has a public chat. Only registered users may post, while guests can view chat.

---

## Requirements
- [Git](https://git-scm.com/downloads) (Optional) 
- [Docker Engine](https://docs.docker.com/engine/install/) and [docker-compose](https://docs.docker.com/compose/install/) (Usually gets installed alongside Docker engine)

---

## Installation and running

1. Clone or [download the repository](https://github.com/nubnab/boltie/archive/refs/heads/main.zip)
```
git clone https://github.com/nubnab/boltie.git && cd ./boltie
```
2. Run docker script:
```
docker compose up -d
```
3. To stop the program from running:
```
docker compose down
```

---

## Usage
To get started, open the webui at http://localhost:4200

In order to stream, you must make an account, then head to settings and copy your stream key. In OBS Studio, set the Stream Service to "Custom..." and paste your Stream key into the **Server** field.

When streaming, make sure to change your OBS settings according to the settings recommended by OvenMediaEngine [here](https://docs.ovenmediaengine.com/quick-start), otherwise you may experience issues related to hitches / stutters.

---

## Uninstallation
```
docker compose down --rmi all --volumes --remove-orphans
```

---

## Tech Stack
- Backend - Java 21+, Spring Boot, Spring Security
- Frontend - Angular 19
- DB - PostgreSQL
- Stream server - OvenMediaEngine
- Messaging Broker - RabbitMQ
- Ops - Docker

---

## Architecture
WIP

---

## Bugs
Several minor bugs, testing is currently underway, see [bugs](https://github.com/nubnab/boltie/issues?q=is%3Aissue%20state%3Aopen%20label%3Abug). 

---

## Future plans
Many features are still missing as the app is still in early development, check [enhancements](https://github.com/nubnab/boltie/issues?q=is%3Aissue%20state%3Aopen%20label%3Aenhancement) to see what is planned. Currently pending a full re-write to clean up code.


