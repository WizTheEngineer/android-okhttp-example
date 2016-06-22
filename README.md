# android-okhttp-example
A simple OkHttp Example using AsyncTask, Loaders, And Handlers

<h3>Overview</h3>
<p>
  This is a simple example project demonstrating the usage of OkHttp in Android. The project is broken up into three
  activities each with an example of how to make a request using OkHttp. The examples include how to make a request using
  AsyncTask, Handlers, and Loaders.
</p>

<h3>Functionality</h3>
<p>
  The sample application consists of a simple one screen layout that includes a Button and a TextView. Upon clicking the
  button data is retrived from a dummy server using the url provided via the EndPointUrl object which is provided by
  the EndPointUrlProvider.getDefaultEndPointUrl static method. Once the JSON data is retrieved, it is displayed in it's raw
  format inside of the TextView.
</p>

<h3>Code Structure</h3>
<p>Three activities, each with a different implementation for making a request to and end-point using OkHttp.</p>
<ul>
  <li>AsyncTaskExampleActivity</li>
  <ul>
    <li> An example using an AsyncTask to load the data in the background and display the result in the UI</li>
  </ul>
  <li>HandlerExampleActivity</li>
  <ul>
    <li> An example using OkHttp's Call object to make an Asynchronous call for the data and a UI Handler to 
      display the result in the UI.
    </li>
  </ul>
  <li>LoaderExampleActivity</li>
  <ul>
    <li> An example using a Loader to load the data in the background and display the result in the UI
      using the LoaderManager.LoaderCallbacks<D>.
    </li>
  </ul>
</ul>
