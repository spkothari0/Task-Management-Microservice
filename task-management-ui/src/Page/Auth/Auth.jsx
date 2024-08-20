import React from 'react';
import './Auth.css';
import SignIn from './SignIn';
import SignUp from './SignUp';

export default function Auth() {

    const [isRegister, setIsRegister] = React.useState(false);
    const togglePannel = () => {
        setIsRegister(!isRegister);
    }
    return (
        <div className='flex justify-center h-screen items-center overflow-hidden'>
            <div className='box lg:max-w-4xl h-96'>
                <div className={`cover ${isRegister ? "rotate-active" : ""}`}>
                    <div className='front'>
                        <img src='https://images.pexels.com/photos/13073600/pexels-photo-13073600.jpeg?auto=compress&cs=tinysrgb&w=1260&h=750&dpr=1'
                            alt='cover'
                        />
                        <div className='text'>
                            <span className='text-1'>
                                Success is built upon well organized tasks !
                            </span>
                            <span className='text-2 text-xs'>Lets get connected</span>
                        </div>
                    </div>
                    <div className='back'>
                        <img src='https://images.pexels.com/photos/12679942/pexels-photo-12679942.jpeg?auto=compress&cs=tinysrgb&w=1260&h=750&dpr=1'
                            alt='cover'
                        />
                    </div>
                </div>
                <div className='forms h-full'>
                    <div className='form-content h-full'>
                        <div className='login-form'>
                            <SignIn togglePanel={togglePannel}/>
                        </div>

                        <div className='signup-form'>
                            <SignUp togglePanel={togglePannel}/>
                        </div>

                    </div>
                </div>
            </div>

        </div>
    )
}