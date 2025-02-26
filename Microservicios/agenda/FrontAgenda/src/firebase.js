import { initializeApp } from "firebase/app";
import {
  getAuth,
  GoogleAuthProvider,
  signInWithPopup,
  signInWithEmailAndPassword,
  createUserWithEmailAndPassword,
  updateProfile,
} from "firebase/auth";
import { getFirestore, doc, getDoc, setDoc } from "firebase/firestore";
import { getAnalytics } from "firebase/analytics";

// Configuración de Firebase
const firebaseConfig = {
  apiKey: "AIzaSyDP40AmwPMIrmKqCHxdWpRGv9JQOhLeMoU",
  authDomain: "pruebaloginreact-angela.firebaseapp.com",
  projectId: "pruebaloginreact-angela",
  storageBucket: "pruebaloginreact-angela.firebasestorage.app",
  messagingSenderId: "25721423912",
  appId: "1:25721423912:web:971d50c758ac8bc6b98d1d",
  measurementId: "G-006NZTPFCY",
};

// Inicializar Firebase
const app = initializeApp(firebaseConfig);
const analytics = getAnalytics(app);

// Obtener la autenticación y la base de datos
const auth = getAuth(app);
const db = getFirestore(app);

// Proveedor de Google
const googleProvider = new GoogleAuthProvider();

// Función para iniciar sesión con Google
const signInWithGoogle = () => signInWithPopup(auth, googleProvider);

// Función para iniciar sesión con email y contraseña
const signInWithEmail = (email, password) =>
  signInWithEmailAndPassword(auth, email, password);

// Función para crear un usuario con email y contraseña
const signUpWithEmail = async (email, password, additionalData) => {
  try {
    // Crear el usuario con email y contraseña
    const userCredential = await createUserWithEmailAndPassword(
      auth,
      email,
      password
    );
    const user = userCredential.user;

    // Actualizar el perfil del usuario con displayName u otros datos
    if (additionalData.displayName) {
      await updateProfile(user, {
        displayName: additionalData.displayName,
      });
    }

    // Crear un documento en Firestore para almacenar datos adicionales del usuario
    await generateUserDocument(user, additionalData);

    return user;
  } catch (error) {
    throw new Error(error.message);
  }
};

// Función para crear o recuperar el documento del usuario en Firestore
const generateUserDocument = async (userAuth, additionalData) => {
  if (!userAuth) return null;

  const userRef = doc(db, "users", userAuth.uid);
  const userSnap = await getDoc(userRef);

  // Si el documento no existe, lo creamos
  if (!userSnap.exists()) {
    const { email, displayName, photoURL } = userAuth;
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

  return userSnap.data();
};

// Exportar funciones
export {
  auth,
  db,
  signInWithGoogle,
  signInWithEmail,
  signUpWithEmail,
  generateUserDocument,
};
