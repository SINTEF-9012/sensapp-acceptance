
# Using SensApp-Acceptance

NOTE: SensApp-acceptance requires Firefox be installed.

SensApp-Acceptance run a series of tests against a deployment of SensApp, 
described in a property file.

    $> java -jar sensapp-acceptance.jar sensapp.properties

In the above example, the given property file 'sensapp.properties'
contains:

    $> cat sensapp.properties
    admin: http://demo.sensapp.org/admin
    notifier: http://demo.sensapp.org/notifier
    registry: http://demo.sensapp.org/registry
    storage: http://demo.sensapp.org/storage
    dispatcher: http://demo.sensapp.org/dispatcher 

where you would have placed the URLs of the endpoints of the SensApp
instance you want to test. A complete example is available at:

    https://github.com/SINTEF-9012/sensapp-acceptance/blob/master/src/test/resources/endPoints-minicloud.properties