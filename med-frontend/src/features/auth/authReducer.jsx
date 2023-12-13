import { createSlice } from "@reduxjs/toolkit";

const authSlice = createSlice({
	name: 'emt',
	initialState: {
		value: {
			auth: {}
		},
	},
	reducers: {
		login: (state, action) => {
			state.value.auth = action.payload
		},
		logout: state => {
			state.value = {
				auth: {}
			}
		}
	}
})


export const { login, logout } = authSlice.actions
export default authSlice.reducer