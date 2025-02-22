FROM airensoft/ovenmediaengine:0.17.3

RUN apt-get update && apt-get install -y gettext-base && rm -rf /var/lib/apt/lists/*

COPY ./conf/Server.xml /opt/ovenmediaengine/bin/origin_conf/Server.xml
COPY ./conf/Server.template.xml /opt/ovenmediaengine/bin/origin_conf/Server.template.xml

COPY oven_server_conf.sh /opt/ovenmediaengine/bin/origin_conf/oven_server_conf.sh
RUN chmod +x /opt/ovenmediaengine/bin/origin_conf/oven_server_conf.sh

ENTRYPOINT ["/opt/ovenmediaengine/bin/origin_conf/oven_server_conf.sh"]

CMD ["/opt/ovenmediaengine/bin/OvenMediaEngine", "-c", "origin_conf"]
