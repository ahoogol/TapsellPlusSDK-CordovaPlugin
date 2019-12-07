var service = "TapsellPlusCordovaInterface";

module.exports = {

initialize : function (appKey, success, error) {
    cordova.exec(success, error, service, "initialize", [appKey]);
},

requestAd : function (zoneId, success, error) {
    cordova.exec(success, error, service, "requestAd", [zoneId]);
},

showAd : function (zoneId, success, error) {
    cordova.exec(success, error, service, "showAd", [zoneId]);
},

setDebugMode : function (debug, success, error) {
    cordova.exec(success, error, service, "setDebugMode", [debug]);
}

};
