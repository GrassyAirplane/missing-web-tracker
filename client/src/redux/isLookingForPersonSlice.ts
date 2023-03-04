import { createSlice } from "@reduxjs/toolkit"

export const isLookingForPersonSlice = createSlice({
    name: "mode",
    initialState: {
        isLookingForPerson: false
    },
    reducers: {
        toggle: (state) => {
            state.isLookingForPerson = !state.isLookingForPerson;
        },
    }
})

export const { toggle } = isLookingForPersonSlice.actions;
export default isLookingForPersonSlice.reducer;