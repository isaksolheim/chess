# chess

## Notes:

### Recreating Google Play Game Services Setup

- Creating a Google Play Game Services project:
  - Create a new App in Google Play console 
  - Create a new Firebase project
  - Create a new Google Play Game Services project, link to Firebase project
  - Configure cloud project for OAuth
  - Configure Credentials on cloud project, Android OAuth
    - required sha1 found with `keytool -keystore ~/.android/debug.keystore -list -v`. Default password is "android"

### Android OAuth Client:

- Authenticate users and access Google Services
