import React, { useState } from 'react';
import axios from 'axios';
import 'react-toastify/dist/ReactToastify.css';
import { toast } from 'react-toastify';

const ManufacturerForm = () => {
  const [manufacturerData, setManufacturerData] = useState({
    name: '',
    companyWebsiteLink: '',
    emailId: '',
    fieldOfWork: 'Software', // Default value
  });


  const handleInputChange = (e) => {
    const { name, value } = e.target;
    setManufacturerData({
      ...manufacturerData,
      [name]: value,
    });
  };

  const handleSubmit = (e) => {
    e.preventDefault();


    axios
      .post('http://localhost:8080/api/addManufacturer', manufacturerData, {
        headers: {
          'Authorization': 'Bearer ' + localStorage.getItem('token')
        }
      })
      .then((response) => {
        // Handle the response (e.g., show a success message)
        toast.success('Manufacturer added successfully!');

      })
      .catch((error) => {
        // Handle the error (e.g., show an error message)
        console.error('Error adding manufacturer:', error);
        toast.error('Error adding manufacturer!');
      });



    // Send the manufacturerData to the backend API for saving to the database

    // After data is successfully added, reset the form data
    setManufacturerData({
      name: '',
      companyWebsiteLink: '',
      emailId: '',
      fieldOfWork: 'Software', // Reset to default
    });

    // Disable the submit button again

  };

  // Function to check if the form data is complete
  const isFormComplete = () => {
    return (
      !manufacturerData.name ||
      !manufacturerData.companyWebsiteLink ||
      !manufacturerData.emailId
    );
  };

  // Enable the submit button when the form data is complete


  return (
    <div className="form-container" style={{'top': '80%'}}>
      <h2 className="form-label">Add Manufaturer</h2>
      <form onSubmit={handleSubmit}>
        <label className="form-label">
          Name:
          <input
            type="text"
            name="name"
            value={manufacturerData.name}
            onChange={handleInputChange}
            className="form-input"
          />
        </label>
        <label className="form-label">
          Company Website Link:
          <input
            type="text"
            name="companyWebsiteLink"
            value={manufacturerData.companyWebsiteLink}
            onChange={handleInputChange}
            className="form-input"
          />
        </label>
        <label className="form-label">
          Email ID:
          <input
            type="text"
            name="emailId"
            value={manufacturerData.emailId}
            onChange={handleInputChange}
            className="form-input"
          />
        </label>
        <label className="form-label">
          Field of Work:
          <select
            name="fieldOfWork"
            value={manufacturerData.fieldOfWork}
            onChange={handleInputChange}
            className="form-input"
          >
            <option value="Software">Software</option>
            <option value="Hardware">Hardware</option>
          </select>
        </label>
        <button type="submit" disabled={isFormComplete()} className="form-button">
          Add Manufacturer
        </button>

      </form>
    </div>
  );
};

export default ManufacturerForm;
