# RestAPICALL
> This is an Simple and more easy android library where you can make api call with out any library like volley.

## Installation Rest API CALL

//Add it in your root build.gradle at the end of repositories:

```bash
dependencyResolutionManagement {
	repositoriesMode.set(RepositoriesMode.FAIL_ON_PROJECT_REPOS)
	repositories {
		mavenCentral()
		maven { url 'https://jitpack.io' }
	}
}
```

Add Dependency Just go to your build.gradle app Module:
//Add the dependency
dependencies {
```bash
	implementation 'com.github.mdnayeemsarker:RestApiCall:1.0.1'
```
}
//

Configuration of First make an Class must be extends Application also create onCreate function and also set this in Class in manifest
```bash
    android:name=".MyApplication"
```
Set Configuration in MyApplication Class
```bash
    String API_BASE_URL = "https://your-domain.com"; // Adjust your api base url
    boolean IS_DEBUG_MODE = true; // set true if you want to get debug log
    boolean IS_FORMAT_RESPONSE = true; // set true if you want to Json Formated response see more for formated response example
    boolean IS_ORGANIZE_RESPONSE = true; // set true for organize response see more for organize response example

    // Api base url initialization
    Config.init(API_BASE_URL); // you mast need init otherwise base url not connected with library

    Config.setDebugMode(IS_DEBUG_MODE);
    Config.setFormatResponseMode(IS_FORMAT_RESPONSE);
    Config.setOrganizeResponseMode(IS_ORGANIZE_RESPONSE);

    // Add dynamic headers
    Config.addHeader("Accept", "application/json"); // optional
    Config.addHeader("Content-Type", "application/x-www-form-urlencoded"); // need for string request
    Config.addHeader("Content-Type", "application/json"); // need for json request
    Config.addHeader("Authorization", "Bearer " + "TOKEN"); // optional
    Config.addHeader("X-Requested-With", "app"); // optional
    Config.addHeader("x-api-key", "app"); // optional

    // Add dynamic parameters
    Config.addParameter("lang", "en"); // optional
    Config.addParameter("timezone", "GMT+6"); // optional

    // Change progress bar colors if you need
    Config.setProgressColors(
            Color.parseColor("#FF5722"), // Start color
            Color.parseColor("#FFC107"), // Center color
            Color.parseColor("#4CAF50")  // End color
    );
```

Check if your project is connected with library or not
After set all config go to your Activity Java class or Fragment Run your application if you get you base url in checkConfiguration log then you are ready to go for get api response using RestApiCall library
```bash
    Log.d("checkConfiguration", Config.getCheckConfig());
    Log.d("debugMode", "" + Config.isDebugMode());
    Log.d("formatResponseMode", "" + Config.isFormatResponse());
    Log.d("organizeResponseMode", "" + Config.isOrganizeMode());
```

For calling an API go to you java or fragment initialize ApiConfig with an instance 
```bash
  ApiConfig apiConfig = new ApiConfig(this);
```

There are 2 types of request format here one is stringRequest or jsonRequest
* For String Request apiConfig.stringRequest must need to set header Config.addHeader("Content-Type", "application/x-www-form-urlencoded"); for string request
* For Json Request apiConfig.jsonRequest must need to set header Config.addHeader("Content-Type", "application/json"); for json request

For Get Request
```bash
    apiConfig.stringRequest((result, response, message, key) -> {
        Log.d("ApiCallResult: ", String.valueOf(result));
        Log.d("ApiCallResponse: ", response);
        Log.d("ApiCallMessage: ", message);
        Log.d("ApiCallKey: ", key);
    }, Method.GET(),"user", new HashMap<>(), true);
```

For POST Request
```bash
    HashMap<String, String> postParams = new HashMap<>();
    postParams.put("name", "MD NAYEEM SARKER");
    postParams.put("email", "dev.ab.nayeem@gmail.com");
    apiConfig.jsonRequest((result, response, message, key) -> {
        Log.d("ApiCallResult: ", String.valueOf(result));
        Log.d("ApiCallResponse: ", response);
        Log.d("ApiCallMessage: ", message);
        Log.d("ApiCallKey: ", key);
    }, Method.POST(),"update-user", postParams, true);
```

For PUT Request
```bash
    HashMap<String, String> putParams = new HashMap<>();
    putParams.put("age", "27");
    apiConfig.jsonRequest((result, response, message, key) -> {
        Log.d("ApiCallResult: ", String.valueOf(result));
        Log.d("ApiCallResponse: ", response);
        Log.d("ApiCallMessage: ", message);
        Log.d("ApiCallKey: ", key);
    }, Method.PUT(),"update-user", putParams, true);
```

For DELETE Request
```bash
    apiConfig.jsonRequest((result, response, message, key) -> {
        Log.d("ApiCallResult: ", String.valueOf(result));
        Log.d("ApiCallResponse: ", response);
        Log.d("ApiCallMessage: ", message);
        Log.d("ApiCallKey: ", key);
    }, Method.DELETE(),"delete", new HashMap<>(), true);
```

For PATCH Request
```bash
    HashMap<String, String> patchParams = new HashMap<>();
    patchParams.put("age", "27");
    apiConfig.jsonRequest((result, response, message, key) -> {
        Log.d("ApiCallResult: ", String.valueOf(result));
        Log.d("ApiCallResponse: ", response);
        Log.d("ApiCallMessage: ", message);
        Log.d("ApiCallKey: ", key);
    }, Method.PATCH(),"delete", patchParams, true);
```

Additional Note you can also call 
* HEAD Request 
* OPTIONS Request 
* TRACE Request 

## Getting To Know MD NAYEEM SARKER

* MD NAYEEM SARKER has a heart of gold.
* MD NAYEEM SARKER is a person with feelings and opinions, but is very easy to work with.
* MD NAYEEM SARKER can be too opinionated at times but is easily convinced not to be.
* Feel free to [learn more about MD NAYEEM SARKER](https://github.com/mdnayeemsarker).

## License

MIT © [MD NAYEEM SARKER](https://github.com/mdnayeemsarker)
