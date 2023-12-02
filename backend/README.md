# dal-production-backend

## Starting tomcat application server
```bash
cd {tomcat-base}/bin
./startup.sh
```

## Deployment path of Project war file
```bash
{tomcat-base}/webapps/dal_production_services.war
```

## Stop tomcat application server
```bash
cd {tomcat-base}/bin
./shutdown.sh
```

## Calling API when deployed at Tomcat server
```bash
curl --header "Content-Type: application/json" \
  --request POST \
  --data '{"email": "ratul.cho@dekkolegacy.com","password": "ratul"}' \
  http://localhost:8090/api/v1/user/login
```
