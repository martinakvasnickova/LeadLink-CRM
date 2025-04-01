import React, { useEffect, useState } from 'react'
import { Link, useParams } from 'react-router-dom';
import Aside from '../../components/nav/Aside'
import NavbarPrivate from '../../components/nav/NavbarPrivate'

import axiosInstance from '../../axiosConfig';
import AddCase from '../../hooks/cases/AddCase';
import EditCase from '../../hooks/cases/EditCase';

export default function Cases() {

    const [businessCases, setBusinessCases] = useState([]);
    const [selectedBusinessCase, setSelectedBusinessCase] = useState(null);

    const {id} = useParams()

    useEffect(()=>{
      loadBusinessCases();
    }, []);

    const loadBusinessCases = async() => {
      const result = await axiosInstance.get('http://localhost:8080/case')
      setBusinessCases(result.data);
    };

    const deleteBusinessCase = async(id) =>{
      await axiosInstance.delete(`http://localhost:8080/case/${id}`)
      loadBusinessCases()
    }

  return (
    <div>
        <Aside/>
        <NavbarPrivate/>

        <main>
          <h2>Případy</h2>

          <button type="button" className="btn btn-outline-dark" data-bs-toggle="modal" data-bs-target="#addCaseModal">
            Přidat Případ
          </button>
          
          <AddCase/>

          <table className="table table-striped table-hover">
          <thead>
            <tr>
              <th scope="col">#</th>
              <th scope="col">Název</th>
              <th scope="col">Cena</th>
            </tr>
          </thead>

          <tbody>
            {businessCases.map((businessCase, index) => (
              <tr key={businessCase.id}>
                <th scope="row">{businessCase.id}</th>
                <td>{businessCase.name}</td>
                <td>{businessCase.price}</td>
                <td>

                  <button className="btn btn-outline-dark mx-2" data-bs-toggle="modal" data-bs-target="#editCaseModal" onClick={() => setSelectedBusinessCase(businessCase)}>
                    Upravit
                  </button>
                  
                  <button className="btn btn-outline-dark mx-2" data-bs-toggle="modal" data-bs-target="#editCaseModal" onClick={() => setSelectedBusinessCase(businessCase)}>
                    Upravit
                  </button>

                  <button className="btn btn-outline-dark mx-2" onClick={()=>deleteBusinessCase(businessCase.id)}>Smazat</button>
                </td>
              </tr>
            ))}
          </tbody>

          </table>

          

        </main>
        
       


        {selectedBusinessCase && <EditCase businessCase={selectedBusinessCase} refreshBusinessCases={loadBusinessCases} />}

    </div>
  )
}
