
# WallPaper App

## How to Run
- Clone the repository for the Wallpaper app from [here](https://github.com/Abhi8358/WallPaper)
- Generate an API key for Pexels from [here](https://www.pexels.com/api/)
- Obtain an API key for Gemini from [here](https://ai.google.dev/gemini-api/docs/api-key)
- Add both API keys to the `secret.properties` file at the project level.
- Finally, run the project to launch the Wallpaper app with the added API keys for Pexels and Gemini integration.

## Videos and ScreenShots

### video links <br>

[flow_screen_recording](https://drive.google.com/file/d/1V8RLi0BRX5OdVq6A3ReFbAphxzjKNRGE/view?usp=sharing) <br>

### ScreenShots <br>

[Screens](https://drive.google.com/drive/folders/17owBwZGxCCpeqmC7wm2EzY7EX1xwnnPv?usp=sharing) <br>


## About

- This wallpaper application adheres to the MVVM (Model-View-ViewModel) architecture and adopts the single activity pattern.
- It leverages Jetpack Compose for building its user interface, ensuring a modern and efficient UI development experience. 
- Dependency injection is facilitated through Hilt, enhancing maintainability and scalability.
- For image loading, the app integrates the Coil library, known for its simplicity and performance in loading images efficiently.
- Media3 is employed for video loading, providing seamless integration and management of video content within the application.


## Features
- The app comprises several screens: Home, Video Collection, Wallpaper Detail, Collection Detail, and Video Detail.
- The Home screen features a grid view layout for loading wallpapers.
- The Video Collection screen showcases a selection of video wallpaper shorts.
- The Collection screen presents a list of wallpaper collections.
- Tapping on any item in the Collection screen navigates the user to the Collection Detail screen, which showcases image and video wallpapers specific to a particular collection.
- Tapping on any wallpaper in the Collection Detail screen redirects the user to the Wallpaper Detail screen.
- The Wallpaper Detail screen offers a high-resolution wallpaper image, along with sections for downloading, image description, and similar images.
- Image descriptions are fetched from the Gemini AI API to enrich the user experience.

## References

- [Hilt dependency injection](https://developer.android.com/training/dependency-injection/hilt-android)
- [Jetpack Compose — Playing Media](https://proandroiddev.com/learn-with-code-jetpack-compose-playing-media-part-3-3792bdfbe1ea)
