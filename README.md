# PlantShop

An e-commerce application where you can buy plants

To build the foundation of the application, I utilized Android Jetpack components. One of these components that I preferred using was the Navigation Component. The Navigation Component simplifies navigation between different screens of the application and provides a single place to manage application navigation.

For registration and login processes, I implemented Firebase Auth, and I used Firestore to store users' profile photos.

To fetch data from the internet, I created a simple API using GitHub Gist and retrieved the data over the internet using the Retrofit library.

Instead of RecyclerView, I opted for ListAdapter to automatically update the data by leveraging the DiffCallback method.

To provide real-time updates in the user interface, I chose to use the LiveData library.

I implemented the functionality of adding products to the cart or favorites by saving the data in SQLite Room database.

For dependency management, I used Hilt Dagger library. Hilt simplifies dependency injection, allowing better management of dependencies between different components of the application and preventing code repetition.

By providing this more professional description, an employer will gain a better understanding of how the application was developed and which components were used.
