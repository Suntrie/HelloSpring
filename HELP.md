#Server & client run

server container creation
docker run -d --name springClick --ulimit nofile=262144:262144 yandex/clickhouse-server 

client container run & delete
docker run -it --rm --link springClick:clickhouse-server yandex/clickhouse-client --host clickhouse-server