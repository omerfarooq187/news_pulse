# News Pulse

News Pulse is a modern Android application designed to keep users informed with the latest trending news and top headlines. Built with Kotlin and Jetpack Compose, it provides a smooth and responsive reading experience with support for dynamic pagination, offline reading, and intuitive UI elements. 

## Features

- **Trending News**: Browse the latest trending news articles from around the world.
- **Top Headlines**: Explore top headlines across various categories.
- **Responsive UI**: Built with Jetpack Compose for a fluid and modern user experience.
- **Pagination**: Efficiently handles large datasets using the Paging library.
- **Offline Support**: Access saved news even without an internet connection.
- **Error Handling**: Graceful error messages and retry mechanisms for failed network requests.
- **Dependency Injection**: Managed by Hilt for modular and testable code.

## Screenshots

<img src="https://github.com/omerfarooq187/news_pulse/blob/main/screenshots/main_screen.png" alt="Main Screen" width="300"/>
<img src="https://github.com/omerfarooq187/news_pulse/blob/main/screenshots/topheadlines_news_screen.png" alt="Top Headlines List" width="300"/>
<img src="https://github.com/omerfarooq187/news_pulse/blob/main/screenshots/news_details_screen.png" alt="News Details Screen" width="300"/>
<img src="https://github.com/omerfarooq187/news_pulse/blob/main/screenshots/politics_news_screen.png" alt="Politics News Screen" width="300"/>
<img src="https://github.com/omerfarooq187/news_pulse/blob/main/screenshots/sports_news_screen.png" alt="Sports News Screen" width="300"/>
## Getting Started

### Prerequisites

To build and run News Pulse, you’ll need:

- Android Studio Bumblebee or later
- Minimum SDK 24
- Kotlin 2.0.0 or later

### Installation

1. **Clone the repository**:
   ```bash
   git clone https://github.com/yourusername/news-pulse.git
   cd news-pulse
### Open in Android Studio

1. Launch Android Studio and select "Open an existing project".
2. Choose the `news-pulse` folder you just cloned.

### Build and Run

1. Connect an Android device or start an emulator.
2. Click "Run" in Android Studio or use `Shift + F10` to build and deploy the app.

### Project Structure

- **data**: Contains data sources and API-related code.
- **domain**: Manages core business logic, data models, and result types.
- **ui**: Composable functions for rendering UI screens.
- **utils**: Utility classes for helpers and constants.
- 
### Dependencies
- **Retrofit** for API integration
- **Jetpack Compose** for UI development
- **Hilt** for dependency injection
- **Paging** for data pagination
- **Coroutines** for asynchronous operations

### Usage
Once the app is installed, users can:

- View the trending news articles on the main screen.
- Scroll through top headlines across different news categories.
- Tap on a news item to read more details in the browser.

### Contributing
We welcome contributions! To contribute:

1. Fork the project.
2. Create a new branch for your feature (`git checkout -b feature-name`).
3. Make your changes and commit (`git commit -m "Add feature"`).
4. Push to your branch (`git push origin feature-name`).
5. Create a pull request.

### Contact
For further information, reach out to the project owner at umarfarooqm871@gmail.com.
