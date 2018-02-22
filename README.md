# DC_File_Management_System

File management system built in Java, based on M. L. Liu's client-server application. My contribution includes the implementation of a message passing protocol. The system allows the user to login, logout, upload, download using Java socket API. It's a 4th year Distributed Computing project.

* Description of the format of message:
  LOGIN 100 username password
  Response Message:
  102 - Login successful
  900 - Incorrect password. Must have minimum 8 characters.
  901 - Login failed.

* Description of the format of message:
  LOGOUT
  Response Message:
  113 - Logged out
  910 - Error while shutting down server.

* Description of the format of message:
  UPLOAD 200 name password contantOfFile
  Response Message:
  202 - Upload successful. Close both windows to see updated Uploads folder.
  920 - Upload failed to execute.
  
* Description of the format of message:
  DOWNLOAD 300 name
  Response Message:
  302 - Downloaded
  930 - Failed to download.


##Screenshots:
![Screenshot](https://github.com/CBITT/DC_File_Management_System/edit/master/ui_sample.PNG?raw=true "Sample UI")
