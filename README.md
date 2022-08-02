# AndroidMVVMApplication
An Android Application Demonstrating MVVM pattern

This application contains a recyclerview which shows the list of pets, and upon clicking on one of the views, it fetches the details about the pet. It focuses on MVVM pattern used.

The data is being fetched locally from a json in the application, which is placed in the resources directory, I have used GSON to deserialize the data, but for the details of each item, here specifically the description of the pet, there is a background task used to fetch the information from url provided in JSON.

I have also created a observer for the items in ViewModel, so that when there is a change in the properties appropriate changes should be shown to the user on the UI.

The properties in the viewmodel are made MutableLive, so that they can help the observer, this would be useful when the data is lost, or refresh is made or new data is added to the list.

This is especially useful when we have to check the working hours of the application, as a background thread can update the value, depending upon the change the ui would be notififed and we restrict the access to the applications

# Things to Note:

Handling of working hours was done at the last moment, i believe there might be a more effecient way to it.
ViewBinding can be used in recyclerview, i was trying with butter knife.
Dependency Injection could have been used by creating a service for checking working hours, deserializing pet list data and get the content description for the pets.
Additional effort can be put in the UI











