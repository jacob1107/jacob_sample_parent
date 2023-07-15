
curl --location 'http://127.0.0.1:8080/hello' \
--header 'Content-Type: application/json' \
--data '{
    "addr": "上海-json"
}'


curl --location 'http://127.0.0.1:8080/hello' \
--form 'addr="上海-from"'