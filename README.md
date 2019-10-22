This is a trivial Android app that interfaces with a Logitech Media
Server.

It does not do this by using the server API, but it provides a web view
on the server, using its Material skin.

Prerequisites:

* A running Logitech Media Server, with Material skin installed.

* A PC with AndroidStudio to build the app.

  You need to set the server
  url (including `/material/mobile`) in
  `app/src/main/res/values/config.xml`, e.g.
  
    <string name="server">http://lms.example.com:9000/material/mobile</string>

Sorry, I haven't figured out yet how to add a preferences screen ;).
PRs welcome!






