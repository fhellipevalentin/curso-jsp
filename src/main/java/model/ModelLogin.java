package model;

import java.io.Serializable;

public class ModelLogin implements Serializable {

    private static final long serialVersionUID = 1L;

    private Long id;
    private String nome;
    private String email;
    private String login;
    private Boolean useradmin;
    private String senha;

    public boolean isNovo(){
        if (this.id == null) {
            return true; // inserir novo
        } else if (this.id > 0) {
            return false; // atualizar
        }
        return false;
    }

    public ModelLogin() {
    }

    public ModelLogin(Long id, String nome, String email, String login, Boolean useradmin, String senha) {
        this.id = id;
        this.nome = nome;
        this.email = email;
        this.login = login;
        this.useradmin = useradmin;
        this.senha = senha;
    }

    public Boolean getUseradmin() {
        return useradmin;
    }

    public void setUseradmin(Boolean useradmin) {
        this.useradmin = useradmin;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getSenha() {
        return senha;
    }

    public void setSenha(String senha) {
        this.senha = senha;
    }
}
