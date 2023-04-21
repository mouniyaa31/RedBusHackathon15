  const firebaseConfig = {
    apiKey: "AIzaSyBc3u_yObQpdlbxvLk-0j3Lj65sbOXXVFE",
    authDomain: "xlr81-3f006.firebaseapp.com",
    databaseURL: "https://xlr81-3f006-default-rtdb.firebaseio.com",
    projectId: "xlr81-3f006",
    storageBucket: "xlr81-3f006.appspot.com",
    messagingSenderId: "1022642523893",
    appId: "1:1022642523893:web:fe232d7ee18b3c1b8a3fbc",
    measurementId: "G-VE0F7BP968"
  };

  // Initialize Firebase
 firebase.initializeApp(firebaseConfig);
  var score=0;
  
  function addScore()
  {
  score = score + 1;
  document.getElementById("score").innerHTML = score;
  }
  
  
