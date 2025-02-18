// src/components/Application.js
import React, { useContext } from "react";
import {
  BrowserRouter as Router,
  Route,
  Routes,
  Navigate,
} from "react-router-dom";
import SignIn from "./SignIn";
import SignUp from "./SignUp";
import UserProvider, { UserContext } from "../provider/UserProvider";
import ProfilePage from "./ProfilePage";
import PasswordReset from "./PasswordReset";

function Application() {
  const user = useContext(UserContext);

  return (
    <UserProvider>
      <Router>
        <Routes>
          {user ? (
            <Route path="/profile" element={<ProfilePage />} />
          ) : (
            <>
              <Route path="/" element={<SignIn />} />
              <Route path="/signUp" element={<SignUp />} />
              {/* <Route path="/passwordReset" element={<PasswordReset />} /> */}
              <Route path="*" element={<Navigate to="/" />} />
            </>
          )}
        </Routes>
      </Router>
    </UserProvider>
  );
}

export default Application;
