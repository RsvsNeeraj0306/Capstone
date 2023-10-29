import {  useNavigate } from "react-router-dom";

import { useState } from "react";
import VisibilityIcon from "@mui/icons-material/Visibility";
import VisibilityOffIcon from "@mui/icons-material/VisibilityOff";
import instance from "../../service/LoginService";
import './Login.css'



const LoginPage = () => {
    const navigate = useNavigate();
    const [showPassword, setShowPassword] = useState(false);
    const [username, setUsername] = useState('');
    const [password, setPassword] = useState('');



    const handleSubmit = async (e) => {
        e.preventDefault();
        if (username === '' || password === '') {
            return;
        }
        await login(username, password);

        setPassword('');
        setUsername('');
    };

    const login = async (username, password) => {
        const res = await instance.post('/api/auth/token', { username, password });
        if (res.status === 200) {
            console.log(res.data);
            localStorage.setItem("token", res.data.token);
            navigate('/');
        }
    };

    const toggleShowPassword = () => {
        setShowPassword(!showPassword);
    };

    return (
        <section className='auth'>
            <div className='wrapper'>
                <div >
                    <form className='auth-form' onSubmit={handleSubmit}>
                        <h3 className='auth-title'>Welcome back</h3>
                        <p className='auth-desc'>Welcome back! Please enter your details</p>
                        <div className='input-group'>
                            <label htmlFor='username' className='label'>
                                username
                            </label>
                            <div className='input-container' data-error='enter valid email'>
                                <input
                                    type='text'
                                    name='username'
                                    required
                                    placeholder='Enter username'
                                    autoComplete='username'
                                    autoFocus='on'
                                    value={username}
                                    onChange={(e) => setUsername(e.target.value)}
                                    id='username'
                                />
                            </div>
                        </div>
                        <div className='input-group'>
                            <label htmlFor='password' className='label'>
                                Password
                            </label>
                            <div className='input-container'>
                                <input
                                    type={showPassword ? "text" : "password"}
                                    name='password'
                                    required
                                    placeholder='Enter your password'
                                    min='6'
                                    value={password}
                                    onChange={(e) => setPassword(e.target.value)}
                                    id='password'
                                />
                                {!showPassword ? (
                                    <VisibilityOffIcon className='pwd-icon' onClick={toggleShowPassword} />
                                ) : (
                                    <VisibilityIcon className='pwd-icon' onClick={toggleShowPassword} />
                                )}
                            </div>
                        </div>
                        <button className='auth-btn' type='submit' id="submit">
                            Sign in
                        </button>
                    </form>
                </div>
            </div>
        </section>
    );
};

export default LoginPage;