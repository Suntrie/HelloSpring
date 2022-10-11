#Server & client run

server container creation
docker run -d --name springClick --ulimit nofile=262144:262144 yandex/clickhouse-server 

client container run & delete
docker run -it --rm --link springClick:clickhouse-server yandex/clickhouse-client --host clickhouse-server 

Connect to the client inside from the container & insert sampling data copied to the root.
clickhouse-client --host 127.0.0.1 --query "INSERT INTO tutorial.recipes SELECT title, JSONExtract(ingredients, 'Array(String)'), JSONExtract(directions, 'Array(String)'), link, source, JSONExtract(NER, 'Array(String)') FROM input('num UInt32, title String, ingredients String, directions String, link String, source LowCardinality(String), NER String') FORMAT CSVWithNames" --input_format_with_names_use_header 0 --format_csv_allow_single_quote 0 --input_format_allow_errors_num 10 < /full_dataset.csv