# ms-spring-cloud-configuration
microservice de configuration des microservices

1-create project with dependency:
   -<dependency>
            <groupId>org.springframework.cloud</groupId>
            <artifactId>spring-cloud-config-server</artifactId>
        </dependency>
 2- application.properties:
     server.port= 9090

        #le chemin qui contient le fichier de configuration de l ensemble des microservice
        #create file in directory c/users/miirb/spring-cloud-config and "git init"
        spring.cloud.config.server.git.uri=file://${user.home}/spring-cloud-config
3-add to main: @EnableConfigServer 

4- MINGW64 => 
    - cd c:
    -cd c/users/miirb/spring-cloud-config 
    - git init (create a repo local)
    -code application.properties (configuration file of this ms)
    - code ms-invoice.properties  (configuration file of other ms)
    -git add .
    -git status
    -git commit -m "first config"

5- copy  all application.properties of other microservice (ex: for invoice ms to ms-invoice.properties file)

6-add to application.properties of invoice ms:
     -change name "application.properties" to "bootstrap.properties"
     -#for microservice of configuration
      # same name as file of configuration of this microservice in spring-configuration-microservice
      spring.application.name=ms-invoice
      #uri used for retrieving its own configuration
      spring.cloud.config.uri=http://localhost:9090

      #enable all endpoint which is enabled by actuator
      management.endpoints.web.exposure.include=*
      
7- ms-invoice POM:
  <!--        spring cloud config for microservice config-->
        <dependency>
            <groupId>org.springframework.cloud</groupId>
            <artifactId>spring-cloud-starter-config</artifactId>
            <version>4.0.0</version>
        </dependency>

        <!-- https://mvnrepository.com/artifact/org.springframework.cloud/spring-cloud-starter-bootstrap -->
        <dependency>
            <groupId>org.springframework.cloud</groupId>
            <artifactId>spring-cloud-starter-bootstrap</artifactId>
            <version>4.0.0</version>
        </dependency>

        <!-- notifier au microservice que sa configuration vient de changer ==> @RefreshScope in controller-->
        <dependency>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-starter-actuator</artifactId>
            <version>3.0.1</version>
        </dependency>
        
8- ms-invoice controller:
    //refresh params of configuration in microservice of configuration if it changed
      //for refresh value : POST => http://localhost:8085/actuator/refresh with headers: Content-type :      Application/json
      
9- retrieve value in  ms-service controller:
  /*************retrieve data from microservices***************
    //http://localhost:8085/actuator
    @Value("${msConfigValue}")
    private int msConfigValue;

    @Value("${msInvoiceValue}")
    private int msInvoiceValue;

    @Value("${email}")
    private String email;
    @GetMapping("/myConfig")
    public Map<String, Object> myConfig(){
        Map<String,Object> params=new HashMap<>();
        params.put("msConfigValue",msConfigValue);
        params.put("msInvoiceValue",msInvoiceValue);
        params.put("email",email);
        params.put("threadName",Thread.currentThread().getName());
        return params;
    }


    @RefreshScope
