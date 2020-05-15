# ProjectName : GithubProfile

The App displays a list of Github Repositories of a particular user using the Github API
for the search query entered. The query will be searched in the Github
repositories. The data is saved in the local database cache that is backed by the network data.
 As the user scrolls to the end of the displayed list, a new network request is triggered
 and the results are displayed and saved in the database.

 This App  here uses -

 *Room - for local database cache.
 *LiveData and ViewModel - for maintaining the data of the App.
 *Retrofit and Okhttp - for communicating with the Github API.
