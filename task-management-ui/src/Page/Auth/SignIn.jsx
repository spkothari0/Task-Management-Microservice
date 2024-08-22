import { Button, TextField } from '@mui/material';
import React from 'react'
import { useDispatch } from 'react-redux';
import { login } from '../../ReduxToolkit/Slices/AuthSlice';
export default function SignIn({togglePanel}) {

    const dispatch = useDispatch();

    const [formData, setFormData] = React.useState({
        email: "",
        password: ""
    });

    const handleChange = (e) => {
        const { name, value } = e.target;
        setFormData({ ...formData, [name]: value });
    }

    const handlSubmit = (e) => {
        e.preventDefault();
        dispatch(login({ username: formData.email, password: formData.password }));
    }

    return (
        <div>
            <h1 className='text-lg font-bold text-center pb-8'>Log In</h1>
            <form className='space-y-3' onSubmit={handlSubmit}>
                <TextField
                    fullWidth
                    label='Email'
                    name='email'
                    // type='email'
                    value={formData.email}
                    placeholder='Enter your email'
                    onChange={handleChange}
                />
                <TextField
                    fullWidth
                    label='Password'
                    name='password'
                    type='password'
                    value={formData.password}
                    onChange={handleChange}
                    placeholder='Enter your password'
                />
                <div>
                    <Button className="customButton" type='submit'
                        fullWidth variant='contained' sx={{ padding: '.9rem' }}>
                        Login
                    </Button>
                </div>
            </form>
            <div className='mt-5 flex items-center gap-2 py-5 justify-center'>
                <span>Dont have an account?</span>
                <Button onClick={togglePanel}>
                    Sign Up
                </Button>
            </div>
        </div>
    )
}