import React, { Component, createContext } from "react";
import { auth, generateUserDocument } from "../firebase";

export const UserContext = createContext(null); // Asegura que el contexto tiene un valor por defecto

class UserProvider extends Component {
  state = {
    user: null,
  };

  componentDidMount() {
    auth.onAuthStateChanged(async (userAuth) => {
      const user = userAuth ? await generateUserDocument(userAuth) : null;
      this.setState({ user });
    });
  }

  render() {
    return (
      <UserContext.Provider value={{ user: this.state.user }}>
        {this.props.children}
      </UserContext.Provider>
    );
  }
}

export default UserProvider;
