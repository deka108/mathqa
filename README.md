# MathQA for Android

## Best Practices:
1. Android Annotation Tool: faster android development using annotations. Powerful dependency injection tools, multi-threading between background and UI threads.
2. Observer pattern: Retrofit2 and RXJava for REST APIs with the MathQA server.
3. Dependency Injection: Parceler, serialize-deserialize Java objects.
4. Incorporate Material Design during UI Development. Here, I made use of the following libraries to develop material design views a better user experience.
  - [Material Values](https://github.com/AoDevBlue/MaterialValues): Used for consistent color and sizes across Android UIs
  - [Flexible Adapters](https://github.com/davideas/FlexibleAdapter/): Used for developing expandable and list views.
  - [Floating Action Buttons](https://github.com/Clans/FloatingActionButton): Used for displaying different search input queries.
  - [Material Dialogs](https://github.com/afollestad/material-dialogs): Used for showing different search dialogs, and creating LaTeX input editor.
  - [Material Icons](https://github.com/mikepenz/Android-Iconics): Used for displaying icons representing certain user actions.
  - [Progress Activity](https://github.com/vlonjatg/progress-activity): Used for displaying progress to the user.
5. Runtime Permission Handler: Android version Marshmallow or later require permissions before allowing the app to use sensitive system data and features. Some of these sensitive features include camera and file system access which is used by MathQA during OCR. [Dexter](https://github.com/Karumi/Dexter) library is incorporated.

## Features (screenshot will be added soon):
- [X] ExpandableViews using Flexible Adapters: header items + content preview subitems
- [X] ListViews using Flexible Adapters: display list of items
- [X] DetailViews using Android activities, Fragments and ViewPager
- [X] LaTeX rendering using KaTeX, alternative views if there is syntax errors
- [X] Java object models 
- [X] RestAPI with Retrofit2 and RxJava observables
  - [X] Progress related views for better UX
    - [X] loading spinner if a request is ongoing
    - [X] display error if there is an error or connection with the server cannot be established
    - [X] empty activity if there is no data available from the server's response
  - [X] REST API services for receiving object models from MathQA database server
  - [X] REST API services for searching mathematical questions
- [X] Search Related Features:
  - [X] Search dialogs:
    - [X] Text input query
    - [X] LaTeX input query
    - [X] Image picker
- [X] **Formula LaTeX Dialog Editor**
- [X] SearchResults: display question results from user's query
- [X] Permission handling: Handles camera and file access permisssions for Android version Marshmallow or later.
- [X] OCR Related Features:
  - [X] Image Picker: select image from file album or image capture from camera
    - [X] Crop selected image
  - [X] Image Preprocessing with Catalano Framework or Pipeline
    - [X] Convert image into a grayscale bitmap
    - [X] Preprocess grayscale bitmaps using binary thresholding, edge detection, deskewing
  - [X] OCR with Tesseract: recognise text from preprocessed bitmaps
  - [X] OCR with Google Text API: recognise text from grayscale bitmaps
