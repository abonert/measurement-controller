# measurement-controller

## Development

To start application simply run:

    ./gradlew bootRun
    
## Demo scenario

[Use postman collection](https://github.com/abonert/measurement-controller/blob/master/measurement-controller.postman_collection.json)
### There are three API
1. POST /api/users - for creating user
2. PUT /api/users - for updating existing user
3. DELETE /api/users/{id} - for removing existing user

## How-to extend configure new vendor
Describe new vendor inside application.yml(could be dynamic configuration in future) [see example 'application.vendor-configurations'](https://github.com/abonert/measurement-controller/blob/master/src/main/resources/application.yml).
To register new vendor data storage implement interface [VendorDataStorage](https://github.com/abonert/measurement-controller/blob/master/src/main/java/software/jevera/measurementcontroller/repository/VendorDataStorage.java).
To register new subscription strategy(e.g. SUBSCRIBER) implement [VendorSubscriptionTask](https://github.com/abonert/measurement-controller/blob/master/src/main/java/software/jevera/measurementcontroller/service/VendorSubscriptionTask.java)
  