## app-pod.yml
Used to create a pod and deploy the application inside the pod
## postgres-pod.yml
Used to creata a pod and deploy postgres container inside the pod
## app-service.yml
Used to forward the application port to outside world
## postgres-service.yml
Used to forward the postgres port to outside world
## app-replica-controller
Used to control replica in a legacy way
## app-replica-sets
Used to control replica in a modern way , the difference between replication controller and sets is specified in docs
## app-deployments
Used to create deployment --> which will create replicaSet upon applying
## postgres-deployments
