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