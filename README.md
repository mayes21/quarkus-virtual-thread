# quarkus-virtual-thread

This project uses Quarkus, the Supersonic Subatomic Java Framework.

If you want to learn more about Quarkus, please visit its website: https://quarkus.io/ .

## Testing virtual thread with K6 script on docker

```shell script
$ docker pull grafana/k6
```

- Create and initialize a new script
```shell script
cd /Users/amayas/Dev/workspace/java/quarkus-virtual-thread/src/test/resources
$ docker run --rm -i -v $PWD:/app -w /app grafana/k6 new
```
- Run k6
```shell script
$ docker run --rm -i grafana/k6 run - <k6-test-script.js
```
- Run a load test with 20 virtual user (20 concurrent threads) and 90s duration
```shell script
$ docker run --rm -i grafana/k6 run --vus 20 --duration 90s - <k6-test-script.js
```


### Testing results 
```shell script
     checks.........................: 100.00% ✓ 3520      ✗ 0
     data_received..................: 256 kB  2.8 kB/s
     data_sent......................: 179 kB  2.0 kB/s
     http_req_blocked...............: avg=80.85µs min=625ns  med=6.45µs  max=15.8ms   p(90)=17.16µs  p(95)=22.62µs
     http_req_connecting............: avg=23.37µs min=0s     med=0s      max=2.51ms   p(90)=0s       p(95)=0s
     http_req_duration..............: avg=26.85ms min=5.35ms med=26.45ms max=106.56ms p(90)=34.74ms  p(95)=38.98ms
       { expected_response:true }...: avg=26.85ms min=5.35ms med=26.45ms max=106.56ms p(90)=34.74ms  p(95)=38.98ms
     http_req_failed................: 0.00%   ✓ 0         ✗ 1760
     http_req_receiving.............: avg=84.39µs min=6.16µs med=27.79µs max=5.04ms   p(90)=161.49µs p(95)=292.65µs
     http_req_sending...............: avg=99.6µs  min=2.87µs med=18.25µs max=10.19ms  p(90)=80.88µs  p(95)=219.32µs
     http_req_tls_handshaking.......: avg=0s      min=0s     med=0s      max=0s       p(90)=0s       p(95)=0s
     http_req_waiting...............: avg=26.67ms min=5.24ms med=26.24ms max=106.43ms p(90)=34.59ms  p(95)=38.91ms
     http_reqs......................: 1760    19.363126/s
     iteration_duration.............: avg=1.03s   min=1s     med=1.03s   max=1.11s    p(90)=1.04s    p(95)=1.04s
     iterations.....................: 1760    19.363126/s
     vus............................: 20      min=20      max=20
     vus_max........................: 20      min=20      max=20
```