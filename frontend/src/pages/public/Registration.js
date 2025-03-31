import React, { useState } from 'react'

import './css/Registration.css'
import { useNavigate } from 'react-router-dom'
import axios from 'axios'

export default function Registration() {

  const [formData, setFormData] = useState({
    firstname:'',
    lastname:'',
    username:'',
    password:'',
    confirmPassword:''
  })

  const [error, setError] = useState('')
  const navigate = useNavigate()

  const handleChange = (e) => {
    setFormData({
      ...formData,
      [e.target.name]: e.target.value
    });
  };

  const handleSubmit = async (e) =>{
    e.preventDefault();

    if(formData.password !== formData.confirmPassword){
      setError('Hesla se neshodují.');
      return;
    }

    setError('')

    try{
      const response = await axios.post('http://localhost:8080/user/register', formData);
      if(response.status === 201){
        navigate('/registration-successful')
      }
      else{
        const errorText = await response.text();
        setError(errorText)
      }
    } catch(err){
      setError('An error occured during user registration.')
    }

  }

  return (
    <div className='registration'>
      <form className='registration-form' onSubmit={handleSubmit}>

        <h2>Registrace</h2>

        <div className="mb-3">
          <label htmlFor="exampleInputEmail1" className="form-label">Jméno</label>
          <input type={'text'} name='firstname' value={formData.firstname} onChange={handleChange} className="form-control" id="firstname" aria-describedby="emailHelp"/>
        </div>

        <div className="mb-3">
          <label htmlFor="exampleInputEmail1" className="form-label">Příjmení</label>
          <input type={'text'} name='lastname' value={formData.lastname} onChange={handleChange} className="form-control" id="lastname" aria-describedby="emailHelp"/>
        </div>

        <div className="mb-3">
          <label htmlFor="exampleInputEmail1" className="form-label">Email</label>
          <input type={'text'} name='username' value={formData.username} onChange={handleChange} className="form-control" id="email" aria-describedby="emailHelp"/>
          <div id="emailHelp" className="form-text">We'll never share your email with anyone else.</div>
        </div>

        <div className="mb-3">
          <label htmlFor="exampleInputPassword1" className="form-label">Heslo</label>
          <input type={'password'} name='password' value={formData.password} onChange={handleChange} className="form-control" id="password"/>
        </div>

        <div className="mb-3">
          <label htmlFor="exampleInputPassword1" className="form-label">Potvrdit heslo</label>
          <input type={'password'} name='confirmPassword' value={formData.confirmPassword} onChange={handleChange} className="form-control" id="confirmPassword"/>
        </div>

        <button type="submit" className="btn btn-primary" >Submit</button>

    </form>
    </div>
  )
}


