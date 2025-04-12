export const isValidEmail = (email: string): boolean => {
    const re = /\S+@\S+\.\S+/;
    return re.test(email);
  };
  
  export const isStrongPassword = (password: string): boolean => {
    return password.length >= 6;
  };
  