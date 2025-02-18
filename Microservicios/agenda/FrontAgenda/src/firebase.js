// import firebase from "firebase/app";
import "firebase/auth";
import "firebase/firestore";
// import { functions } from "firebase";
// Importar Firebase y los módulos necesarios
import { initializeApp } from "firebase/app";
import { getAuth, GoogleAuthProvider, signInWithPopup } from "firebase/auth";
import { getFirestore, doc, getDoc, setDoc } from "firebase/firestore";


/*
const firebaseConfig = {
  apiKey: "AIzaSyDP40AmwPMIrmKqCHxdWpRGv9JQOhLeMoU",
  authDomain: "pruebaloginreact-angela.firebaseapp.com",
  projectId: "pruebaloginreact-angela",
  storageBucket: "pruebaloginreact-angela.firebasestorage.app",
  messagingSenderId: "25721423912",
  appId: "1:25721423912:web:02739f97d4d2ada4b98d1d",
  measurementId: "G-V6QS74GD5S"
};
// Initialize Firebase
firebase.initializeApp(firebaseConfig);

*/
//////////////////////////////////////////////////////////



  // Your web app's Firebase configuration
 const firebaseConfig = {
   apiKey: "AIzaSyDP40AmwPMIrmKqCHxdWpRGv9JQOhLeMoU",
   authDomain: "pruebaloginreact-angela.firebaseapp.com",
   projectId: "pruebaloginreact-angela",
   storageBucket: "pruebaloginreact-angela.firebasestorage.app",
   messagingSenderId: "25721423912",
   appId: "1:25721423912:web:02739f97d4d2ada4b98d1d",
   measurementId: "G-V6QS74GD5S",
 };
  // Initialize Firebase
  firebase.initializeApp(firebaseConfig);





/////////////////////////////////////////////////////////

export const auth = firebase.auth();
export const firestore = firebase.firestore();

const provider = new firebase.auth.GoogleAuthProvider();
export const signInWithGoogle = () => {
  auth.signInWithPopup(provider);
};

export const generateUserDocument = async (user, additionalData) => {
  if (!user) return;

  const userRef = firestore.doc(`users/${user.uid}`);
  const snapshot = await userRef.get();

  if (!snapshot.exists) {
    const { email, displayName, photoURL } = user;
    try {
      await userRef.set({
        displayName,
        email,
        photoURL,
        ...additionalData
      });
    } catch (error) {
      console.error("Error creating user document", error);
    }
  }
  return getUserDocument(user.uid);
};

const getUserDocument = async uid => {
  if (!uid) return null;
  try {
    const userDocument = await firestore.doc(`users/${uid}`).get();

    return {
      uid,
      ...userDocument.data()
    };
  } catch (error) {
    console.error("Error fetching user", error);
  }
};
