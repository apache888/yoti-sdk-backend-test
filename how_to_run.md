1. Open unzipped project folder or clone the project.

2. run 'maven clean install'.
   
3. run 'docker-compose up --build'

* if you want to change something in project
- run 'maven clean install'
- run 'docker-compose down'
- run 'docker rmi hoover-app'
- run 'docker-compose up --build'