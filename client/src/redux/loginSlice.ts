import { createSlice } from "@reduxjs/toolkit"

export const loginSlice = createSlice({
    name: "authentication",
    initialState: {
        loggedIn: false
    },
    reducers: {
        login: (state) => {
            state.loggedIn = true;
        },
        logout: (state) => {
            state.loggedIn = false;
        }
    }
})

export const { login, logout } = loginSlice.actions;
export default loginSlice.reducer;