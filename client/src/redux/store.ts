import { configureStore } from "@reduxjs/toolkit"
import loginReducer from './loginSlice'

// export default store = configureStore({
    
// })

export const store = configureStore({
    reducer: {
        logger: loginReducer
    }
})

export type RootState = ReturnType<typeof store.getState>
// export type AppDispatch = typeof store.dispatch