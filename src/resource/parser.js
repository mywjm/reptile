system = require('system')  
address = system.args[1];
var page = require('webpage').create();  
var url = address;  

page.settings.userAgent = 'Mozilla/5.0 (compatible; MSIE 8.0; Windows NT 5.1; Trident/4.0; .NET CLR2.0.50727)';
page.settings.resourceTimeout = 1000*10; // 10 seconds
page.onResourceTimeout = function(e) {
    console.log(page.content);      
    phantom.exit(1);
};

page.open(url, function (status) {  
    //Page is loaded!  
    if (status !== 'success') {  
        console.log('Unable to post!');  
    } else {  
        console.log(page.content);
    }
    phantom.exit(); 
  });