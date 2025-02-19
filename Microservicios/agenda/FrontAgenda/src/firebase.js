import { initializeApp } from "firebase/app";
import { getAuth, GoogleAuthProvider, signInWithPopup } from "firebase/auth";
import { getFirestore, doc, getDoc, setDoc } from "firebase/firestore";

// Configuración de Firebase
const firebaseConfig = {
  apiKey: "AIzaSyDP40AmwPMIrmKqCHxdWpRGv9JQOhLeMoU",
  authDomain: "pruebaloginreact-angela.firebaseapp.com",
  projectId: "pruebaloginreact-angela",
  storageBucket: "pruebaloginreact-angela.appspot.com",
  messagingSenderId: "25721423912",
  appId: "1:25721423912:web:02739f97d4d2ada4b98d1d",
  measurementId: "G-V6QS74GD5S",
};

// Inicializar Firebase
const app = initializeApp(firebaseConfig);
export const auth = getAuth(app);
export const firestore = getFirestore(app);

// Proveedor de autenticación con Google
const provider = new GoogleAuthProvider();
export const signInWithGoogle = async () => {
  try {
    await signInWithPopup(auth, provider);
  } catch (error) {
    console.error("Error al iniciar sesión con Google", error);
  }
};

// Generar documento de usuario
export const generateUserDocument = async (user, additionalData) => {
  if (!user) return null;

  const userRef = doc(firestore, `users/${user.uid}`);
  const snapshot = await getDoc(userRef);

  if (!snapshot.exists()) {
    const { email, displayName, photoURL } = user;
    try {
      await setDoc(userRef, {
        displayName,
        email,
        photoURL,
        ...additionalData,
      });
    } catch (error) {
      console.error("Error creando documento de usuario", error);
    }
  }

  return getUserDocument(user.uid);
};

// Obtener documento de usuario
const getUserDocument = async (uid) => {
  if (!uid) return null;
  try {
    const userRef = doc(firestore, `users/${uid}`);
    const userDocument = await getDoc(userRef);
    return { uid, ...userDocument.data() };
  } catch (error) {
    console.error("Error obteniendo usuario", error);
  }
};
