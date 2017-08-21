/*

User-Agent: Fiddler
Authorization: Bearer 11111
Host: localhost:8080
Origin: http://localhost
Content-Length: 0



*/
$(document).ready(init2);

function init2(){
    $.ajax(
          {type : "GET",
		   url:"http://localhost:8080/api/users",
           headers : {"Authorization" : "Bearer " + authzSession.accessToken},
           contentType : "application/json;charset=UTF-8",
           //data : requestTxt,
           dataType : "json",
           success : updateScreen,
           error : handleAuthzSessionHttpError});
}

function updateScreen(users){
 users.forEach(function(user) {
 //    var node = "<div>" + user.name + "</div>" ;
   //document.getElementById("myDiv").appendChild(node);
     //var para = document.createElement("div");                       // Create a <p> node
//var t = document.createTextNode(user.name);      // Create a text node
  //   para.appendChild(t);  
//document.getElementById("myDiv").appendChild(t);
  // document.getElementById("myDiv").innerHTML = "<div><a href='www.google.com'>a</></div>" ;
     var divElement = $('<div id="userFile" />').text(user.name);
     $('body').append(divElement);//'<div id="test">test</div><div id="afterAjax" style="display:none">'+ user.name+'</div>')
});
// document.getElementById("myDiv").innerHTML = "<script type='text/javascript'>alert('test');</script>";//message[0].name;
}



$("input:file").change(function(objEvent) {
   var data = new FormData(document.getElementById("yourFormID")); // your form ID
var url = jQuery("#yourFormID").attr("action"); // your form action 
$.ajax({
    url: "http://localhost:8080/api/game",
    headers : {"Authorization" : "Bearer " + authzSession.accessToken},
    type: "POST",
    data: data, // this will get all the input fields of your form.
    //enctype: 'multipart/form-data',
    processData: false,  // tell jQuery not to process the data
    contentType: false,   // tell jQuery not to set contentType
    dataType: 'json', // as you want
    success: function(response) {        
        alert('Success!!') 
    },
    error: function(er){
        alert(er);
    }
});
});

//http://docs.identityserver.io/en/release/quickstarts/7_javascript_client.html#refjavascriptquickstart
/*
function log() {
    document.getElementById('results').innerText = '';

    Array.prototype.forEach.call(arguments, function (msg) {
        if (msg instanceof Error) {
            msg = "Error: " + msg.message;
        }
        else if (typeof msg !== 'string') {
            msg = JSON.stringify(msg, null, 2);
        }
        document.getElementById('results').innerHTML += msg + '\r\n';
    });
}

document.getElementById("login").addEventListener("click", login, false);
document.getElementById("api").addEventListener("click", api, false);
document.getElementById("logout").addEventListener("click", logout, false);
//client_secret=WUJsHNZDVRYfM8aN_UGO9V2T5OTcvjE0le4iht9-83A
//client_id: "22r75kndm255m",
var config = {
    authority: "http://127.0.0.1:8080/c2id/",
    client_id: "22r75kndm255m",
    redirect_uri: "http://127.0.0.1/wchess/callback.html",
    response_type: "code",
    scope:"openid profile api1",
    post_logout_redirect_uri : "http://127.0.0.1/wchess//index.html"
};
var mgr = new Oidc.UserManager(config);

mgr.getUser().then(function (user) {
    if (user) {
        log("User logged in", user.profile);
    }
    else {
        log("User not logged in");
    }
});

function login() {
    mgr.signinRedirect();
}

function api() {
    mgr.getUser().then(function (user) {
        var url = "http://127.0.0.1:8080/c2id/userinfo";

        var xhr = new XMLHttpRequest();
        xhr.open("GET", url);
        xhr.onload = function () {
            log(xhr.status, JSON.parse(xhr.responseText));
        }
        xhr.setRequestHeader("Authorization", "Bearer " + user.access_token);
        xhr.send();
    });
}

function logout() {
    mgr.signoutRedirect();
}*/
/**
 * Connect2id login page JavaScript
 *
 * Requires Connect2id server version 5.0+
 *
 * Important: This JavaScript code is intended for tutorial and development
 * purposes only. For production use the logic and API calls of the login pages
 * must be implemented in server-side code.
 */


// -- Configuration and global vars -- //
//https://bitbucket.org/connect2id/connect2id-login-page-js/src/c5a4c80d5fb223d29b97ba2a443ec85ee0cc2a76/src/main/webapp/?at=master
// Connect2id authorisation session API vars
// See http://connect2id.com/products/server/docs/integration/authz-session
var authzSession = {};
authzSession.baseURL = "http://127.0.0.1:8080/c2id/authz-sessions/rest/v3"; // The endpoint
authzSession.accessToken = "ztucZS1ZyFKgh0tUEruUtiSTXhnexmd6"; // Token to access the endpoint
authzSession.id; // Will store the authorisation session ID

// Connect2id user session store API vars
// See http://connect2id.com/products/server/docs/integration/session-store
var subjectSession = {};
subjectSession.baseURL = "http://127.0.0.1:8080/c2id/session-store/rest/v2"; // The endpoint
subjectSession.accessToken = "ztucZS1ZyFKgh0tUEruUtiSTXhnexmd6"; // Token to access the endpoint
subjectSession.id; // Will store the session ID from the session cookie

// LdapAuth API var (username + password verification against LDAP directory)
// See http://connect2id.com/products/ldapauth/web-api
var ldapAuth = {};
ldapAuth.url = "http://127.0.0.1:8080/ldapauth/"; // The endpoint
ldapAuth.apiKey = "f70defbeb88141f88138bea52b6e1b9c"; // Key to access the endpoint

// Subject (end-user) ID and other details for UI personalisation
var subject = {};
subject.id;
subject.name;
subject.email;


// Initialises the OpenID Connect login page. Submits the OpenID authentication
// request (encoded in the URI query string) and the session cookie (if any) to
// the Connect2id server. The Connect2id server will then process the request,
// and if necessary, ask the front-end to authenticate the subject (end-user), 
// or obtain the subject's consent. When the OpenID authentication response is 
// ready, the Connect2id server will ask the front-end to relay it back to the
// client (typically via a 302 HTTP redirect). For more information see 
// http://connect2id.com/products/server/docs/integration/authz-session#authz-sessions-post

function init() {

	clearUI();
	resetGlobalVars();
	attachEventHandlers();

	// Extract the URL query string with the encoded OpenID authentication request
	var queryString = location.search;

	if (! queryString) {
	   // handleError("Invalid OpenID Connect authentication request: Missing query string");
   		//return;
    }

	log("Query string: " + queryString);

    // Check for a session cookie
    //subjectSession.id = Cookies.get("sid");
    log("Found session cookie: " + (subjectSession.id ? subjectSession.id : "none"));

    // Start new Connect2id authorisation session, by submitting the OpenID
    // request for processing along with the session ID from the cookie (if any)
    var request = {};
    //request.query = queryString;
    //request.sub_sid = subjectSession.id;

    //var requestTxt = JSON.stringify(request, null, 4);
	var requestTxt =   
	"response_type=code&scope=openid&client_id=22r75kndm255m&redirect_uri=http://127.0.0.1/wchess/callback.html";
    log("Making authorization session start request to the Connect2id server: " + requestTxt.query);
	var config = {   
    client_id: "22r75kndm255m",
    redirect_uri: "http://127.0.0.1/wchess/callback.html",
    response_type: "code",
    scope:"openid profile api1",
    post_logout_redirect_uri : "http://127.0.0.1/wchess//index.html"
};
var requestTxt = {
	query : JSON.stringify(config, null, 4)
};

   /* $.ajax({
           type : "POST",
		   url:authzSession.baseURL,
            headers : {"Authorization" : "Bearer " + authzSession.accessToken},
            contentType : "application/json",
            data : {
  query : "response_type=code&scope=openid email&client_id=s6BhdR&state=af0ifjsldkj&redirect_uri=https://client.example.org/cb"
},
            dataType: "json",
            success : switchAction,
            error : handleAuthzSessionHttpError});*/
			
$.ajax({
  method: "POST",
  url: authzSession.baseURL,
   headers : {"Authorization" : "Bearer " + authzSession.accessToken},
  contentType : "application/json",
  data: {query:"response_type=code&scope=openid email&client_id=s6BhdR&state=af0ifjsldkj&redirect_uri=https://client.example.org/cb"}
}).done(function( msg ) {
    log("dentro");
  });
}


// Performs four possible actions, depending on the type of response from the
// Connect2id server:
//     1. Prompt the end-user authenticate;
//     2. Prompt the end-user for consent;
//     3. Return the final OAuth response to the client;
//     4. Display non-redirectable error.
function switchAction(c2idServerResponse) {

    // Save the authorisation session ID if set
	
    if (c2idServerResponse.sid) {
        authzSession.id = c2idServerResponse.sid;
        log("Started Connect2id authorisation session with ID " + authzSession.id);
    }

    log("Received " + c2idServerResponse.type + " message: " + JSON.stringify(c2idServerResponse, null, 4));

    if (c2idServerResponse.display == "popup") {
        sizeWindow();
    }

    if (c2idServerResponse.type == "auth") {
        displayLoginForm(c2idServerResponse);
    } else if (c2idServerResponse.type == "consent") {
        displayConsentForm(c2idServerResponse);
    } else if (c2idServerResponse.type == "response") {
        relayAuthzResponse(c2idServerResponse);
    } else if (c2idServerResponse.type == "error") {
        displayErrorMessage(c2idServerResponse);
    }
}


// Authenticates the subject (end-user) by submitting the entered username and
// password to the LdapAuth JSON-RPC 2.0 service (for LDAP authentication)
function authenticateSubject() {

	var username = $("#login-username").val();
	var password = $("#login-password").val();
	
	log("Entered credentials: username=" + username + " password=" + password);
	
	if (username.length === 0 || password.length === 0) {
		alert("You must enter a username and a password");
		$("#login-username").focus();
		return;
	}
	
	// Clear login form
	$("#login-username").val("");
	$("#login-password").val("");

	// Make a user.auth request to the LdapAuth JSON-RPC 2.0 service
	var jsonRpcRequest = {};
	jsonRpcRequest.method = "user.authGet";
	jsonRpcRequest.params = {};
	jsonRpcRequest.params.username = username;
	jsonRpcRequest.params.password = password;
    jsonRpcRequest.params.apiKey = ldapAuth.apiKey;
	jsonRpcRequest.id = 1;
	jsonRpcRequest.jsonrpc = "2.0";
	
	var jsonRpcRequestTxt = JSON.stringify(jsonRpcRequest, null, 4);
	
	log("Making " + jsonRpcRequest.method + " request to the LdapAuth service: " + jsonRpcRequestTxt);

	$.ajax({type : "POST",
		   url:ldapAuth.url,
        	contentType : "application/json;charset=UTF-8",
        	data : jsonRpcRequestTxt,
        	dataType : "json",
        	success : handleLdapAuthResponse,
        	error : handleLdapAuthHttpError});
}


// Handles the subject (end-user) authentication response from the LdapAuth
// JSON-RPC 2.0 service. On success subject (end-user) ID is submitted to the 
// Connect2id server, along with a few other details to create a new subject 
// (end-user) session
function handleLdapAuthResponse(jsonRpcResponse) {

	if (jsonRpcResponse.error) {
		// Authentication failed
		log("Authentication failed: " + JSON.stringify(jsonRpcResponse, null, 4));
		alert(jsonRpcResponse.error.message);
		return;
	}

	log("Subject successfully authenticated: " + JSON.stringify(jsonRpcResponse, null, 4));

	subject.id = jsonRpcResponse.result.attributes.userID;
	log("Subject ID: " + subject.id);

	subject.name = jsonRpcResponse.result.attributes.name;
	log("Subject name: " + subject.name);
	$("#subject-name").text(subject.name).html();
	$("#subject-name").css("display", "inline");

	subject.email = jsonRpcResponse.result.attributes.email[0];
	log("Subject email: " + subject.email);
	$("#subject-email").text(subject.email).html();
    $("#subject-email").css("display", "inline");

	// Hide the login form
    $("#login-form").css("display", "none");

	// Submit the subject (end-user) details to the Connect2id server
	var request = {};
    request.sub = subject.id;
    request.acr = "http://loa.c2id.com/basic"; // Optional Authentication Context Class Reference (ACR)
    request.amr = [ "pwd" ]; // Optional Authentication Method References (AMR)
    request.claims = {}; // Optional claims to save in the session object
    request.claims.login_ip = "10.20.30.40";
    request.claims.browser = navigator.appName;
    request.data = {}; // Store optional session data about the subject
    request.data.name = subject.name;
    request.data.email = subject.email;

    var requestTxt = JSON.stringify(request, null, 4);

    log("Submitting subject authentication to Connect2id server: " + requestTxt);

    $.ajax(
          {type : "PUT",
		  url:authzSession.baseURL + "/" + "siAnaiJLAN-LB8q8B-HBV3eVaEApYhEVX0TlaJc7sAA" + "?ajax=true",
           headers : {"Authorization" : "Bearer " + authzSession.accessToken},
           contentType : "application/json;charset=UTF-8",
           data : requestTxt,
           dataType : "json",
           success : switchAction,
           error : handleAuthzSessionHttpError});
}


// Handles HTTP error responses from the LdapAuth service
function handleLdapAuthHttpError(jqXHR, textStatus, errorThrown) {

    handleError("LdapAuth error: HTTP " + textStatus + ": " + JSON.stringify(jqXHR, null, 4));
}


// Logs out the subject (end-user) and returns to the login screen
function logoutSubject() {

    Cookies.remove("sid");

    log("Submitting subject logout request to Connect2id server");

    $.ajax(
        {type : "DELETE",
		url:subjectSession.baseURL + "/sessions",
         headers : { "Authorization" : "Bearer " + subjectSession.accessToken,
	                 "SID" : subjectSession.id },
         success : init,
         error : handleLogoutHttpError });
}


// Handles HTTP error responses from the Connect2id session store
function handleLogoutHttpError(jqXHR, textStatus, errorThrown) {

    handleError("Logout error: HTTP " + textStatus + ": " + JSON.stringify(jqXHR, null, 4));
    log(msg);
}


// Submits the subject (end-user) consent to the Connect2id server. On success
// the Connect2id server will ask the front-end to relay the final OpenID 
// authentication response back to the client (typically via a 302 HTTP 
// redirect)
function submitConsent() {

    var consentedScopeValues = [];
    $(".scope-value:checked").each(function() {
        consentedScopeValues.push(this.value);
    });

    log("Consented scope values: " + consentedScopeValues);
	var consentedClaims = [];
	
	$(".claim:checked").each(function() {
		consentedClaims.push(this.value);
	});

	log("Consented claims: " + consentedClaims);

	// Compose the final authorisation from the consent form and other details
	var authz = {};
	authz.scope = consentedScopeValues; // May add additional scope values here
	authz.claims = consentedClaims; // May add additional claims here
	authz.preset_claims = {};
	authz.preset_claims.id_token = {}; // Optional preset claims to return with the ID token
	authz.preset_claims.userinfo = {}; // Optional preset claims to return with UserInfo
	authz.preset_claims.userinfo.groups = ["admin", "audit"];
	authz.audience = []; // Optional custom audience values
	authz.long_lived = true;
	authz.id_token = {};
	authz.id_token.lifetime = 600; // Override the default ID token lifetime (to 10 minutes)
	// authz.id_token.impersonated_sub = "bob"; // optional subject to impersonate
	authz.refresh_token = {};
	authz.refresh_token.issue = true;
	authz.refresh_token.lifetime = 0; // no expiration
	authz.access_token = {};
	authz.access_token.encoding = "SELF_CONTAINED"; // IDENTIFIER or SELF_CONTAINED
	authz.access_token.lifetime = 600; // Override the default access token lifetime (to 10 minutes)
	authz.access_token.encrypt = false; // Optional flag to encrypt the access token (for a self-contained one)

	var requestTxt = JSON.stringify(authz, null, 4);

	log("Submitting authorization to Connect2id server: " + requestTxt);

    $.ajax(
           {type : "PUT",
		   url:authzSession.baseURL + "/" + authzSession.id + "?ajax=true",
            headers : {"Authorization" : "Bearer " + authzSession.accessToken},
            contentType : "application/json;charset=UTF-8",
            data : requestTxt,
            dataType : "json",
            success : switchAction,
            error : handleAuthzSessionHttpError});
}


// The subject (end-user) denies the authorisation request
function denyAuthorization() {
	
	log("Authorization denied, submitting request to Connect2id server...");
	
	$.ajax(
	       {type:"DELETE",
		   url:authzSession.baseURL + "/" + authzSession.id + "?ajax=true",
	        headers : {"Authorization" : "Bearer " + authzSession.accessToken},
	        success : switchAction,
            error : handleAuthzSessionHttpError});
}


// Relays the final OpenID authentication response back to the client. The 
// query, fragment and form_post response modes are supported.
function relayAuthzResponse(c2idServerResponse) {

    // Update session cookie if indicated by the Connect2id server
    var newSubjectSessionID = c2idServerResponse.sub_sid;

    if (newSubjectSessionID) {
        subjectSession.id = newSubjectSessionID;
        Cookies.set("sid", subjectSession.id);
        log("Set session cookie: " + subjectSession.id);
    }

    if (c2idServerResponse.mode == "query" || c2idServerResponse.mode == "fragment") {

        log("Redirection URI: " + c2idServerResponse.parameters.uri);
        window.location.replace(c2idServerResponse.parameters.uri);
        
    } else if (c2idServerResponse.mode == "form_post") {
    
        $("#form-post-response").attr("action", c2idServerResponse.parameters.uri);

        log("Form post URI: " + c2idServerResponse.parameters.uri);

        for (var paramName in c2idServerResponse.parameters.form) {

            var el = "<input type=\"hidden\" name=\"" + paramName + "\" value=\"" + c2idServerResponse.parameters.form[paramName] + "\"/>";

            $("#form-post-response").append(el);

            log("Form post parameter: " + paramName + ": " + c2idServerResponse.parameters.form[paramName]);
        }

        $("#form-post-response").submit();
    }
}



// --- Helper functions --- //

// Clears the UI
function clearUI() {

    $("#subject-name").css("display", "none");
    $("#subject-name").html("");

    $("#subject-email").css("display", "none");
    $("#subject-email").html("");

    $("#logout-button").css("display", "none");

    $("#login-form").css("display", "none");
    $("#login-username").val("");
    $("#login-password").val("");

    $("#consent-form").css("display", "none");
    $("#client-metadata td").html("not specified");
    $("#authz-details td").html("none");

    $("#error-message").css("display", "none");
}


// Resizes window for display=popup
function sizeWindow() {

    var targetWidth = 800;
    var targetHeight = 600;

    var currentWidth = $(window).width();
    var currentHeight = $(window).height();

    window.resizeBy(targetWidth - currentWidth, targetHeight - currentHeight);

    $("body").css("overflow-y", "scroll");
}


// Resets the global vars
function resetGlobalVars() {

    authzSession.id = null;
    subjectSession.id = null;
    subject = {};
}

// Attach event handlers
function attachEventHandlers() {

    // Ensure we don't have duplicate handlers
    $("#login-button").off();
    $("#login-button").click(authenticateSubject);

    $("#logout-button").off();
    $("#logout-button").click(logoutSubject);

    $("#consent-button").off();
    $("#consent-button").click(submitConsent);

    $("#deny-button").off();
    $("#deny-button").click(denyAuthorization);
}


// Handles Connect2id authorisation session error responses
function handleAuthzSessionHttpError(jqXHR, textStatus, errorThrown) {

    handleError("Authorization session error: HTTP " + textStatus + ": " + JSON.stringify(jqXHR, null, 4));
}


// Displays the login form to the get the subject's credentials
function displayLoginForm(form) {

     log("Displaying login form...");
     $("#login-form").css("display", "block");
     if (form.login_hint) {
        // The client supplied a login hint
        $("#login-username").val(form.login_hint);
        $("#login-password").focus();
     } else {
        $("#login-username").focus();
     }
}


// Displays the consent form to get the subject's consent for the scope and
// claims requested by the client
function displayConsentForm(form) {

    // Set/update subject session cookie
    subjectSession.id = form.sub_session.sid;
    Cookies.set("sid", subjectSession.id);
    log("Set session cookie: " + subjectSession.id);

    $("#logout-button").css("display", "inline");

    if (form.sub_session.data) {

        $("#subject-name").text(form.sub_session.data.name).html();
        $("#subject-name").css("display", "inline");

        $("#subject-email").text(form.sub_session.data.email).html();
        $("#subject-email").css("display", "inline");
    }

    log("Displaying consent form...");
    $("#consent-form").css("display", "block");

    // Display the OpenID Connect client details
    $("#client-name").text(form.client.name).html();
    $("#client-id").text(form.client.client_id).html();
    $("#client-type").text(form.client.client_type + " client").html();
    $("#client-uri").text(form.client.uri).html();
    $("#client-logo-uri").text(form.client.logo_uri).html();
    $("#client-policy-uri").text(form.client.policy_uri).html();
    $("#client-tos-uri").text(form.client.tos_uri).html();

    // Display the requested scope values
    if (form["scope"]["new"].length > 0) {

        $("#new-scope-values").html("");

        form["scope"]["new"].forEach(function(value){

            var disabled = (value=="openid")? "disabled" : "";
            var checked = (value=="openid")? "checked=\"true\"" : "";

            var el = "<input class=\"scope-value\" type=\"checkbox\" " +
                checked + " " +
                disabled +
                " value=\"" + value + "\"/>" +
                "&nbsp;" + value + " ";

            $("#new-scope-values").append(el);
        });
    }

    if (form["scope"]["consented"].length > 0) {

        $("#consented-scope-values").html("");

        form["scope"]["consented"].forEach(function(value){

            var disabled = (value=="openid")? "disabled" : "";

            var el = "<input class=\"scope-value\" type=\"checkbox\" " +
                "checked=\"true\" " +
                disabled +
                " value=\"" + value + "\"/>" +
                "&nbsp;" + value + " ";

             $("#consented-scope-values").append(el);
        });
    }


    // Display the requested claims (resolved from the scope values or explicit)
    if (form["claims"]["new"]["essential"].length > 0) {

        $("#new-essential-claims").html("");

        form["claims"]["new"]["essential"].forEach(function(claim){

            var el = "<input class=\"claim\" type=\"checkbox\" value=\"" + claim + "\"/>" +
                "&nbsp;" + claim + " ";

            $("#new-essential-claims").append(el);
        });
    }

    if (form["claims"]["new"]["voluntary"].length > 0) {

        $("#new-voluntary-claims").html("");

        form["claims"]["new"]["voluntary"].forEach(function(claim){

            var el = "<input class=\"claim\" type=\"checkbox\" value=\"" + claim + "\"/>" +
                "&nbsp;" + claim + " ";

            $("#new-voluntary-claims").append(el);
        });
    }

    // Display the requested claims (resolved from the scope values or explicit)
    if (form["claims"]["consented"]["essential"].length > 0) {

        $("#new-essential-claims").html("");

        form["claims"]["consented"]["essential"].forEach(function(claim){

            var el = "<input class=\"claim\" type=\"checkbox\" checked=\"true\" value=\"" + claim + "\"/>" +
                "&nbsp;" + claim + " ";

            $("#new-essential-claims").append(el);
        });
    }

    if (form["claims"]["consented"]["voluntary"].length > 0) {

        $("#consented-voluntary-claims").html("");

        form["claims"]["consented"]["voluntary"].forEach(function(claim){

            var el = "<input class=\"claim\" type=\"checkbox\" checked=\"true\" value=\"" + claim + "\"/>" +
                "&nbsp;" + claim + " ";

            $("#consented-voluntary-claims").append(el);
        });
    }
}


// Handles an error
function handleError(errorMessage) {
    log(errorMessage);
    displayErrorMessage({"error_description":errorMessage});
}


// Displays an error message
function displayErrorMessage(errorObject) {

    $("#error-message").css("display", "block");

    if (errorObject.error) {
        $("#error-code").text("[ " + errorObject.error + " ] ").html();
    }

    if (errorObject.error_description) {
        $("#error-description").text(errorObject.error_description).html();
    } else {
        $("#error-description").text("Invalid OpenID Connect request").html();
    }
}


// Logs a message to the console text area
function log(msg) {
	
	var txt = $("#console");
	txt.val( txt.val() + msg + "\n");
}