import { Button, FormControl, InputLabel, MenuItem, Select, TextField } from '@mui/material';
import React from 'react'
import { useDispatch } from 'react-redux';
import { register } from '../../ReduxToolkit/Slices/AuthSlice';


const roles = [{ label: 'Admin', value: 'ROLE_ADMIN' }, { label: 'Customer', value: 'ROLE_CUSTOMER' }, { label: 'Helper', value: 'ROLE_HELPER' }]

export default function SignUp({ togglePanel }) {
    const [formData, setFormData] = React.useState({
        firstName: "",
        lastName: "",
        email: "",
        role: "",
        username: "",
        password: "",

    });

    const handleChange = (e) => {
        const { name, value } = e.target;
        setFormData({ ...formData, [name]: value });
    }

    const dispatch = useDispatch();

    const handlSubmit = (e) => {
        e.preventDefault();
        dispatch(register(formData));
        // clear form data
        setFormData({
            firstName: "",
            lastName: "",
            email: "",
            role: "",
            username: "",
            password: "",
        });
        console.log("Register form data: ", formData);
    }

    return (
        <div>
            <h1 className='text-lg font-bold text-center pb-6'>Register</h1>
            <form className='space-y-3' onSubmit={handlSubmit}>
                <TextField
                    fullWidth
                    label='First Name'
                    name='firstName'
                    type='text'
                    value={formData.firstName}
                    placeholder='Enter your first name'
                    onChange={handleChange}
                    size='small'
                />
                <TextField
                    fullWidth
                    size='small'
                    label='Last Name'
                    name='lastName'
                    type='text'
                    value={formData.lastName}
                    placeholder='Enter your last name'
                    onChange={handleChange}
                />
                <FormControl fullWidth size='small'>
                    <InputLabel id="role">Role</InputLabel>
                    <Select
                        size='small'
                        labelId="Role"
                        id="Role"
                        name='role'
                        value={formData.role}
                        label="Role"
                        onChange={handleChange}
                    >
                        {roles.map((role) => (
                            <MenuItem value={role.value}>{role.label}</MenuItem>
                        ))}
                    </Select>
                </FormControl>
                <TextField
                    size='small'
                    fullWidth
                    label='Email Id'
                    name='email'
                    type='email'
                    value={formData.email}
                    placeholder='Enter your email'
                    onChange={handleChange}
                />
                <TextField
                    fullWidth
                    size='small'
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
                        Register
                    </Button>
                </div>
            </form>
            <div className='mt-5 flex items-center gap-2 py-5 justify-center'>
                <span>Already have an account?</span>
                <Button onClick={togglePanel}>
                    Sign In
                </Button>
            </div>
        </div>
    )
}