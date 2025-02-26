#!/bin/sh

if [ -z "$OVEN_API_USERNAME" ]; then
  echo "OVEN_API_USERNAME environment variable is not set. Using default value."
  export OVEN_API_USERNAME="root"
else
  echo "Using OVEN_API_USERNAME from environment variable: $OVEN_API_USERNAME"
fi

if [ -z "$OVEN_API_PASSWORD" ]; then
  echo "OVEN_API_PASSWORD environment variable is not set. Using default value."
  export OVEN_API_PASSWORD="pass"
else
  echo "Using OVEN_API_PASSWORD from environment variable: $OVEN_API_PASSWORD"
fi

if [ -z "$OVEN_STREAM_GEN_KEY" ]; then
  echo "OVEN_STREAM_GEN_KEY environment variable is not set. Using default value."
  export OVEN_STREAM_GEN_KEY="aKq#1kj"
else
  echo "Using OVEN_STREAM_GEN_KEY from environment variable: $OVEN_STREAM_GEN_KEY"
fi

sed -i "s/\${OVEN_API_USERNAME}/${OVEN_API_USERNAME}/g" /opt/ovenmediaengine/bin/origin_conf/Server.template.xml
sed -i "s/\${OVEN_API_PASSWORD}/${OVEN_API_PASSWORD}/g" /opt/ovenmediaengine/bin/origin_conf/Server.template.xml
sed -i "s/\${OVEN_STREAM_GEN_KEY}/${OVEN_STREAM_GEN_KEY}/g" /opt/ovenmediaengine/bin/origin_conf/Server.template.xml

mv /opt/ovenmediaengine/bin/origin_conf/Server.template.xml /opt/ovenmediaengine/bin/origin_conf/Server.xml

exec "$@"



