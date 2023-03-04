import { configureStore } from "@reduxjs/toolkit"
import loginReducer from './loginSlice'
import modeReducer from './isLookingForPersonSlice'

// export default store = configureStore({
    
// })

export const store = configureStore({
    reducer: {
        logger: loginReducer,
        toggler: modeReducer
    }
})

export type RootState = ReturnType<typeof store.getState>
// export type AppDispatch = typeof store.dispatch