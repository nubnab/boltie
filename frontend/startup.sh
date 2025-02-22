#!/bin/sh

if [ -z "$API_URL" ]; then
  echo "API_URL environment variable is not set. Using default value."
  export API_URL="http://localhost:8080"
else
  echo "Using API_URL from environment variable: $API_URL"
fi

envsubst < /usr/share/nginx/html/environments/env.template.js > /usr/share/nginx/html/environments/env.js

exec nginx -g "daemon off;"
