FROM node:22 AS build

WORKDIR /app
COPY package*.json ./
RUN npm install
COPY . .

RUN npm run build --omit=dev

FROM nginx:latest
COPY --from=build /app/dist/frontend/browser /usr/share/nginx/html
COPY nginx_frontend.conf /etc/nginx/conf.d/default.conf
