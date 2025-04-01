import React, { useEffect, useState } from 'react'
import { Link, useParams } from 'react-router-dom';
import Aside from '../../components/nav/Aside'
import NavbarPrivate from '../../components/nav/NavbarPrivate'

import axiosInstance from '../../axiosConfig';

export default function Cases() {

    const [businessCases, setBusinesssCases] = useState([]);
    const [selectedBusinessCases, setSelectedBusinessCase] = useState(null);

    const {id}=useParams()

     useEffect(() => {
        loadBusinessCases();
    }, []);

    const loadBusinessCases = async () => {
        const result = await axiosInstance.get('http://localhost:8080/case');
        setBusinesssCases(result.data);
    };

    const deleteBusinessCase=async(id)=>{
    await axiosInstance.delete(`http://localhost:8080/case/${id}`)
    loadBusinessCases()
    }

  return (
    <div>
        <Aside/>
        <NavbarPrivate/>

        
        



    </div>
  )
}
