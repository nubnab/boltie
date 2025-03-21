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

## Uninstallation
```
docker compose down --rmi all --volumes --remove-orphans
```

---

## Tech Stack
