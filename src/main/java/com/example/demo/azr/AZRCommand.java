package com.example.demo.azr;

import com.fasterxml.jackson.databind.JsonNode;

public class AZRCommand {
    static public String inputAccountName; //"kisiaastronaccount"; //스토리지 계정
    static public String inputResourceGroupName; //"kisia-astron"; //스토리지 계정

    //id, resourceGroup, name(account)

    static public String idValue ;

    static public JsonNode storageAccountList ;



    //final 대문자
    static public final String POWERSHELL = "powershell.exe";

    static public final String SPACE = " ";

    static public final String DOT = ".";

    static public final String EQUAL = "=";

    static public final String AZ = "az";

    static public final String RESOURCE = "resource";

    static public final String IDS = "--ids";
    static public final String LOGIN = "login";
    static public final String STORAGE = "storage";
    static public final String WEBAPP = "webapp";
    static public final String ACCOUNT = "account";
    static public final String LIST = "list";
    static public final String BLOB = "blob";

    static public final String TABLE = "table";
    static public final String SERVICE_PROPERTIES = "service-properties";
    static public final String DELETE_POLICY = "delete-policy";
    static public final String ACTIVITY_LOG = "activity-log";

    static public final String SHOW = "show";

    static public final String UPDATE = "update";
    static public final String ACCOUNT_NAME = "--account-name";
    static public final String ACCOUNT_KEY = "--account-key";
    static public final String RESOURCE_GROUP = "--resource-group";
    static public final String AUTH_MODE = "--auth-mode";
    static public final String DAYS_RETAINED = "--days-retained";
    static public final String NAME = "--name";
    static public final String ENABLE = "--enable";
    static public final String LOGGING = "logging";
    static public final String SERVICES = "--services";

    static public final String QUERY = "--query";

    static public final String SET = "--set";
    static public final String LOG = "--log";
    static public final String KEY = "--key";
    static public final String RETENTION = "--retention";
    static public final String KEYS = "keys";
    static public final String B = "b";
    static public final String T = "t";
    static public final String G = "-g";
    static public final String N = "-n";
    static public final String SECURITY = "security";
    static public final String MONITOR = "monitor";
    static public final String ALERT = "alert";
    static public final String PROPERTIES = "properties";
    static public final String PRICING_TIER = "pricingTier";
    static public final String ENABLED = "enabled";
    static public final String CLIENT_CERT_ENABLE = "clientCertEnabled";
    static public final String HTTPS_ONLY = "httpsOnly";
    static public final String PRICING = "pricing";
    static public final String SETTING = "setting";
    static public final String RENEW = "renew";
    static public final String AUTO_PROVISIONING_SETTING = "auto-provisioning-setting";
    static public final String APPSERVICES = "AppServices";
    static public final String SQLSERVERVIRTUALMACHINES = "SqlServerVirtualMachines";
    static public final String CONTAINER_REGISTRY = "ContainerRegistry";
    static public final String WDATP = "WDATP";
    static public final String KEYPOLICY = "keyPolicy";
    static public final String RWD = "rwd";
    static public final String AUTOPROVISION = "autoProvision";
    static public final String NETWORKRULESET = "networkRuleSet";
    static public final String NETWORKACLS = "networkAcls";
    static public final String BYPASS = "bypass";
    static public final String DEFAULT = "default";
    static public final String DEFAULTACTION = "defaultAction";
    static public final String STANDARD = "standard";
    static public final String TRUE = "true";
    static public final String ON = "On";
    static public final String ALLOW = "Allow";
    static public final String DENY = "Deny";
    static public final String AZURESERVICES = "Azureservices";
    static public final String MINTLSVERSION = "minTlsVersion";
    static public final String MIN_TLS_VERSION = "--min-tls-version";
    static public final String MIN_TLS_VERSION_STANDARD = "1.2";
    static public final String DAYS90 = "90";
    static public final String DAYS30 = "30";
    static public final String KEY1 = "key1";
    static public final String KEY_EXPIRATION_PERIOD_IN_DAYS = "keyExpirationPeriodInDays";
    static public final String KEY_EXPIRATION_PERIOD_IN_DAYS60 = "60";
    static public final String POLICYASSIGNMENTS_DELETE = "[?condition.allOf[?equals == 'Microsoft.Authorization/policyAssignments/delete']]";






    static public final String OUTPUT = "--output";
    static public final String JSON = "json";



}
